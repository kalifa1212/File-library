/* =========================================================
   HLS Streaming Dashboard — logic
   Adapte les URLs d'endpoints (UPLOAD_ENDPOINT, STREAM_BASE)
   à ton contrôleur Spring Boot si les chemins diffèrent.
   ========================================================= */

const UPLOAD_ENDPOINT = "/upload"; // POST multipart/form-data, champ "file"
const STREAM_BASE = "/stream";                // GET /stream/{videoId}/{*filename}

// ---------- Helpers génériques ----------

function setBox(el, msg, type) {
    el.textContent = msg;
    el.className = "status" + (type ? " " + type : "");
}

function logTo(el, msg) {
    const line = document.createElement("div");
    const time = new Date().toLocaleTimeString();
    line.textContent = `[${time}] ${msg}`;
    el.prepend(line);
}

function showResult(el, content) {
    el.textContent = content;
    el.classList.remove("hidden");
}

// =========================================================
// SECTION 1 — Upload de fichier
// =========================================================

const uploadForm = document.getElementById("uploadForm");
const fileInput = document.getElementById("fileInput");
const uploadResult = document.getElementById("uploadResult");
const uploadProgressTrack = document.getElementById("uploadProgressTrack");
const uploadProgressFill = document.getElementById("uploadProgressFill");
const uploadSubmitBtn = document.getElementById("uploadSubmitBtn");

uploadForm.addEventListener("submit", function (e) {
    e.preventDefault();

    const file = fileInput.files[0];
    if (!file) {
        showResult(uploadResult, "⚠️ Sélectionne d'abord un fichier.");
        return;
    }

    const formData = new FormData();
    formData.append("file", file);

    const xhr = new XMLHttpRequest();
    xhr.open("POST", UPLOAD_ENDPOINT, true);

    uploadSubmitBtn.disabled = true;
    uploadProgressTrack.classList.add("active");
    uploadProgressFill.style.width = "0%";
    showResult(uploadResult, `⏳ Envoi de "${file.name}" (${formatBytes(file.size)})...`);

    xhr.upload.addEventListener("progress", function (evt) {
        if (evt.lengthComputable) {
            const pct = Math.round((evt.loaded / evt.total) * 100);
            uploadProgressFill.style.width = pct + "%";
        }
    });

    xhr.onload = function () {
        uploadSubmitBtn.disabled = false;
        uploadProgressTrack.classList.remove("active");

        if (xhr.status >= 200 && xhr.status < 300) {
            let body = xhr.responseText;
            try {
                const json = JSON.parse(body);
                body = JSON.stringify(json, null, 2);
                // Si le backend renvoie un videoId, on pré-remplit les sections suivantes
                if (json.videoId) {
                    document.getElementById("streamUrl").value =
                        `${window.location.origin}${STREAM_BASE}/${json.videoId}/playlist.m3u8`;
                    document.getElementById("linkVideoId").value = json.videoId;
                }
            } catch (_) {
                // réponse non-JSON, on affiche tel quel
            }
            showResult(uploadResult, `✅ Upload réussi (HTTP ${xhr.status})\n${body}`);
        } else {
            showResult(uploadResult, `❌ Échec de l'upload (HTTP ${xhr.status})\n${xhr.responseText}`);
        }
    };

    xhr.onerror = function () {
        uploadSubmitBtn.disabled = false;
        uploadProgressTrack.classList.remove("active");
        showResult(uploadResult, "❌ Erreur réseau pendant l'upload. Vérifie que le serveur tourne et que CORS est configuré.");
    };

    xhr.send(formData);
});

function formatBytes(bytes) {
    if (bytes === 0) return "0 B";
    const units = ["B", "KB", "MB", "GB"];
    const i = Math.floor(Math.log(bytes) / Math.log(1024));
    return `${(bytes / Math.pow(1024, i)).toFixed(1)} ${units[i]}`;
}

// =========================================================
// SECTION 2 — Lecteur HLS (hls.js)
// =========================================================

const video = document.getElementById("video");
const statusEl = document.getElementById("status");
const logEl = document.getElementById("log");
let hls = null;

function loadStream() {
    const url = document.getElementById("streamUrl").value.trim();
    if (!url) {
        setBox(statusEl, "⚠️ Merci de saisir une URL de playlist .m3u8", "error");
        return;
    }

    if (hls) {
        hls.destroy();
        hls = null;
    }

    logTo(logEl, `Chargement: ${url}`);
    setBox(statusEl, "Chargement du flux...", "info");

    if (Hls.isSupported()) {
        hls = new Hls({ debug: false });

        hls.loadSource(url);
        hls.attachMedia(video);

        hls.on(Hls.Events.MANIFEST_PARSED, (event, data) => {
            logTo(logEl, `Manifest chargé : ${data.levels.length} qualité(s) disponible(s)`);
            setBox(statusEl, "✅ Manifest chargé, lecture en cours", "ok");
            video.play().catch((e) => logTo(logEl, "Autoplay bloqué: " + e.message));
        });

        hls.on(Hls.Events.FRAG_LOADED, (event, data) => {
            logTo(logEl, `Segment chargé: ${data.frag.relurl}`);
        });

        hls.on(Hls.Events.ERROR, (event, data) => {
            logTo(logEl, `ERREUR [${data.type}] ${data.details}`);
            if (data.fatal) {
                setBox(statusEl, `❌ Erreur fatale: ${data.details}`, "error");
                switch (data.type) {
                    case Hls.ErrorTypes.NETWORK_ERROR:
                        logTo(logEl, "Tentative de reconnexion réseau...");
                        hls.startLoad();
                        break;
                    case Hls.ErrorTypes.MEDIA_ERROR:
                        logTo(logEl, "Tentative de récupération média...");
                        hls.recoverMediaError();
                        break;
                    default:
                        hls.destroy();
                        break;
                }
            }
        });
    } else if (video.canPlayType("application/vnd.apple.mpegurl")) {
        video.src = url;
        video.addEventListener("loadedmetadata", () => {
            setBox(statusEl, "✅ Lecture native HLS (Safari)", "ok");
            video.play().catch((e) => logTo(logEl, "Autoplay bloqué: " + e.message));
        });
        video.addEventListener("error", () => {
            setBox(statusEl, "❌ Erreur de lecture native", "error");
        });
        logTo(logEl, "Utilisation du lecteur HLS natif (Safari/iOS)");
    } else {
        setBox(statusEl, "❌ HLS non supporté par ce navigateur", "error");
    }
}

// =========================================================
// SECTION 3 — Constructeur de lien de streaming / téléchargement
// (s'appuie sur GET /stream/{videoId}/{*filename})
// =========================================================

const linkResult = document.getElementById("linkResult");

function buildStreamUrl() {
    const videoId = document.getElementById("linkVideoId").value.trim();
    const filename = document.getElementById("linkFilename").value.trim() || "playlist.m3u8";

    if (!videoId) {
        showResult(linkResult, "⚠️ Merci de renseigner un videoId.");
        return null;
    }

    const url = `${window.location.origin}${STREAM_BASE}/${encodeURIComponent(videoId)}/${filename}`;
    showResult(linkResult, url);
    return url;
}

function openStreamLink() {
    const url = buildStreamUrl();
    if (url) window.open(url, "_blank");
}

function downloadStreamLink() {
    const url = buildStreamUrl();
    if (!url) return;

    const a = document.createElement("a");
    a.href = url;
    a.download = document.getElementById("linkFilename").value.trim() || "download";
    document.body.appendChild(a);
    a.click();
    a.remove();
}

function useInPlayer() {
    const url = buildStreamUrl();
    if (!url) return;
    document.getElementById("streamUrl").value = url;
    loadStream();
}

// =========================================================
// SECTION 4 — Aperçu rapide (image / audio directs)
// =========================================================

const previewResult = document.getElementById("previewBox");

function loadPreview() {
    const videoId = document.getElementById("linkVideoId").value.trim();
    const filename = document.getElementById("linkFilename").value.trim();

    if (!videoId || !filename) {
        previewResult.innerHTML = `<div class="status error">⚠️ videoId et nom de fichier requis.</div>`;
        return;
    }

    const url = `${window.location.origin}${STREAM_BASE}/${encodeURIComponent(videoId)}/${filename}`;
    const ext = filename.split(".").pop().toLowerCase();

    if (["png", "jpg", "jpeg", "webp", "gif"].includes(ext)) {
        previewResult.innerHTML = `<img src="${url}" alt="preview">`;
    } else if (["mp3", "wav", "aac", "m4a"].includes(ext)) {
        previewResult.innerHTML = `<audio controls src="${url}"></audio>`;
    } else if (ext === "mp4") {
        previewResult.innerHTML = `<video controls src="${url}" style="width:100%"></video>`;
    } else {
        previewResult.innerHTML = `<div class="status info">Type "${ext}" non prévisualisable ici — utilise "Ouvrir" ou "Télécharger".</div>`;
    }
}

// ---------- Init ----------

window.addEventListener("load", () => {
    logTo(logEl, "Page prête. Modifie l'URL puis clique sur \"Charger\".");
});
