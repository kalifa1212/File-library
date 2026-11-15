
# Hforge File Library

### Bibliothèque Java de gestion, de stockage et de traitement avancé des fichiers multimédias

**Hforge File Library** est une bibliothèque Java conçue pour simplifier l'intégration de fonctionnalités de gestion et de traitement de fichiers dans les applications Java et Spring Boot.

Elle fournit une base réutilisable pour gérer les fichiers, traiter les images, les vidéos et les fichiers audio, ainsi que réaliser des opérations de compression, de conversion et de génération de flux vidéo HLS.

L'objectif est de permettre aux développeurs d'intégrer facilement des fonctionnalités multimédias avancées sans avoir à réimplémenter toute la logique technique dans chaque application.

---

## Fonctionnalités principales

### 1. Gestion des fichiers

- Téléversement et téléchargement de fichiers.
- Stockage et récupération des fichiers.
- Gestion des métadonnées.
- Détection du type MIME.
- Organisation et gestion des fichiers.
- Prise en charge de différents modes de stockage, selon les implémentations disponibles.

### 2. Traitement des images

- Redimensionnement des images.
- Compression et optimisation.
- Conversion entre différents formats.
- Génération de miniatures (thumbnails).
- Extraction des informations et métadonnées des images.

### 3. Traitement des vidéos

- Compression vidéo.
- Conversion et transcodage.
- Extraction des métadonnées.
- Génération de miniatures.
- Traitement des fichiers vidéo.
- Préparation des vidéos pour une diffusion en streaming.

### 4. Traitement audio

- Compression audio.
- Conversion entre différents formats.
- Extraction des métadonnées.
- Traitement des fichiers audio.

### 5. Streaming vidéo HLS

- Génération de playlists HLS.
- Découpage des vidéos en segments.
- Préparation de contenus pour le streaming adaptatif.
- Possibilité de définir plusieurs profils de qualité, selon les fonctionnalités implémentées.

---

## Technologies utilisées

| Technologie | Utilisation |
|---|---|
| Java 17 | Langage de développement |
| Spring Boot | Intégration avec les applications Spring |
| Maven | Gestion des dépendances et compilation |
| FFmpeg | Traitement multimédia |
| FFprobe | Analyse des fichiers multimédias |
| REST API | Intégration avec les applications web, si exposée |

---

## Prérequis

Avant d'utiliser cette bibliothèque, assure-toi de disposer des éléments suivants :

- Java 17 ou une version compatible.
- Maven.
- Spring Boot, si tu l'utilises dans une application Spring.
- FFmpeg et FFprobe pour les fonctionnalités de traitement multimédia qui en dépendent.

**Important :** FFmpeg et FFprobe doivent être installés et accessibles dans l'environnement d'exécution de l'application qui utilise la bibliothèque. Leur présence uniquement dans l'environnement de compilation ne suffit pas.

---

## Installation

### Option 1 : Installation locale avec Maven

Si tu développes la bibliothèque localement et souhaites l'utiliser dans un autre projet, commence par installer le package dans ton dépôt Maven local :

```bash
mvn clean install
```

Ajoute ensuite la dépendance dans le fichier `pom.xml` de ton application :

```xml
<dependency>
    <groupId>com.hforge</groupId>
    <artifactId>file-library-starter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

Cette méthode est adaptée au développement local. Pour utiliser la bibliothèque sur un serveur distant ou dans un pipeline CI/CD, elle doit être publiée dans un dépôt Maven accessible, par exemple Maven Central, GitHub Packages ou JitPack.

---

## Utilisation dans une application Spring Boot

Après avoir ajouté la dépendance, tu peux intégrer les composants de la bibliothèque dans ton application.

Exemple conceptuel d'utilisation :

```java
@Service
public class DocumentService {

    private final FileStorageService fileStorageService;

    public DocumentService(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    public void uploadDocument(MultipartFile file) {
        // Exemple d'utilisation du service de stockage
        fileStorageService.store(file);
    }
}
```

**Remarque :** `FileStorageService` et sa méthode `store()` sont utilisés ici à titre illustratif. Adapte cet exemple aux classes, interfaces et méthodes réellement exposées par la bibliothèque.

---

## Configuration

La bibliothèque peut nécessiter une configuration spécifique selon les fonctionnalités activées.

Exemple de configuration indicative :

```yaml
hforge:
  file:
    storage:
      type: local
      location: ./uploads

    processing:
      enabled: true

    multimedia:
      ffmpeg-path: /usr/bin/ffmpeg
      ffprobe-path: /usr/bin/ffprobe
```

Les propriétés présentées sont des exemples de configuration. Il faut les adapter aux véritables propriétés reconnues par l'implémentation de la bibliothèque.

---

## Intégration avec Docker

Pour utiliser les fonctionnalités multimédias dans une application Dockerisée, il faut installer FFmpeg dans l'image finale de l'application.

Exemple pour une image basée sur Debian :

```dockerfile
FROM eclipse-temurin:17-jre

RUN apt-get update \
    && apt-get install -y ffmpeg \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY target/application.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

Cette configuration permet à l'application d'accéder aux exécutables FFmpeg et FFprobe depuis son environnement d'exécution.

---

## Cas d'utilisation

Hforge File Library peut être intégrée dans différents types de projets :

- Plateformes de gestion documentaire.
- Applications de partage de fichiers.
- Réseaux sociaux.
- Plateformes de streaming vidéo.
- Applications de formation en ligne.
- Applications mobiles disposant d'un backend Java.
- Systèmes de gestion de contenus multimédias.
- Plateformes communautaires.

### Exemple de projet : MIRA Network

Dans le cadre de MIRA Network, cette bibliothèque peut servir de composant commun pour centraliser :

- La gestion des images de profil.
- Le stockage des documents.
- La gestion des ressources multimédias.
- Le traitement des vidéos et des audios.
- La préparation des contenus pour le streaming.

Elle permet ainsi de mutualiser les fonctionnalités multimédias entre les différents modules de l'écosystème.

---

## Architecture et conception

La bibliothèque a vocation à être :

- **Modulaire :** organiser les fonctionnalités en composants réutilisables.
- **Réutilisable :** faciliter son intégration dans plusieurs applications.
- **Évolutive :** permettre l'ajout de nouveaux traitements.
- **Extensible :** faciliter l'intégration de nouveaux mécanismes de stockage et de traitement.
- **Compatible avec l'écosystème Java :** s'intégrer aux projets Maven et Spring Boot.

---

## Évolutions envisagées

Voici quelques pistes d'amélioration possibles :

- Intégration du stockage cloud AWS S3.
- Prise en charge de MinIO.
- Traitement multimédia asynchrone.
- Gestion avancée des profils de qualité HLS.
- Génération de formes d'onde audio (waveforms).
- Mise en place de files de traitement.
- Amélioration de la gestion des métadonnées.
- Gestion avancée des événements et des erreurs.
- Suivi de l'état des traitements multimédias.

---

## Contribution

Les contributions sont les bienvenues.

Pour contribuer :

1. Forker le dépôt.
2. Créer une branche pour votre fonctionnalité.
3. Effectuer les modifications.
4. Ajouter les tests nécessaires.
5. Soumettre une Pull Request avec une description claire des changements.

---

## Auteur

**Hforge**

Entreprise spécialisée dans le développement de solutions numériques, la conception d'applications et l'intégration de technologies modernes.

---

## Licence

Définir ici la licence retenue pour le projet (MIT, Apache 2.0 ou autre).

---

**Hforge File Library — Simplifier la gestion et le traitement des fichiers multimédias dans vos applications Java.**
