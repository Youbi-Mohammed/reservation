# Guide Ultra-Détaillé du Projet - Reservation App

Ce guide est conçu pour t'accompagner pas à pas dans la compréhension technique de ton application. Il explique non seulement **comment** le projet est construit, mais surtout la **philosophie** et les **bonnes pratiques** derrière chaque choix technique pour un développeur débutant.

---

## 🏗 1. Architecture Logicielle : Le Pattern MVVM

Nous utilisons le pattern **MVVM (Model-View-ViewModel)**, le standard de l'industrie recommandé par Google.

### Pourquoi cette séparation ?
Dans le passé, les développeurs mettaient tout le code dans l'"Activité" (le fichier principal). Cela créait des fichiers gigantesques (souvent appelés "God Objects") très difficiles à maintenir. MVVM sépare les responsabilités :

- **Model (`data/Model/`)** : Ce sont tes objets de données purs. Ils ne font rien d'autre que définir à quoi ressemble une information.
    - `User.kt` : Définit un utilisateur (nom, prénom, email, téléphone, genre, date de naissance).
    - `Field.kt` : Définit les caractéristiques d'un terrain de sport (nom, prix, note, adresse, catégorie).
    - `Reservation.kt` : Définit une réservation (terrain, date, heure, prix, statut).
- **ViewModel (`ViewModel/`)** : C'est le "cerveau" de chaque écran.
    - Il prépare les données pour l'interface.
    - Il gère les actions de l'utilisateur (clics, saisie de texte).
    - **Avantage majeur** : Il survit aux rotations de l'écran. Si tu tournes ton téléphone, le ViewModel garde tes données en mémoire, contrairement à l'Activité qui est détruite et recréée.
- **View (`Ui/Screen/`)** : Ce sont tes fonctions `@Composable`.
    - Elles ne font qu'afficher ce que le ViewModel leur donne.
    - Elles sont "déclaratives" : tu décris à quoi l'interface doit ressembler pour un état donné, et Compose s'occupe de la dessiner.

---

## 📦 2. Gestion de l'État : StateFlow et Réactivité

L'application est entièrement réactive grâce à `StateFlow`. 

### Pourquoi utiliser StateFlow ?
Imagine un robinet d'eau : la donnée "coule" du ViewModel vers l'interface. 
1. **Dans le ViewModel** : On utilise un `MutableStateFlow` (ex: `_uiState`) pour stocker et mettre à jour les données. Le `_` signifie que c'est une variable privée que seul le ViewModel peut modifier.
2. **Dans l'Écran (View)** : On utilise `collectAsState()`. Cela transforme le flux de données en un "État Compose".
3. **La Recomposition** : Dès que la donnée change dans le ViewModel, Compose détecte le changement et redessine automatiquement uniquement les parties de l'écran qui en ont besoin. C'est magique et ultra-fluide !

---

## 🏛 3. Le Repository : La Source Unique de Vérité (SSOT)

Le fichier `UserRepository.kt` (dans `data/Repository/`) est un **Singleton** (déclaré avec le mot-clé `object`).

### Pourquoi un Singleton ?
- **Le problème** : Chaque écran a son propre ViewModel. Si l'utilisateur saisit son nom sur l'écran d'inscription, le ViewModel de l'écran de profil ne le connaîtra pas car il est indépendant.
- **La solution** : Le `UserRepository` est un "objet global" partagé par toute l'application. L'écran de saisie y "dépose" le profil, et l'écran d'accueil ou de profil vont le "lire" là-bas. C'est ce qui permet de garder ton prénom affiché partout sans avoir à le passer manuellement d'un écran à l'autre.

---

## 🎨 4. Composants UI Réutilisables (Design System)

Pour maintenir une cohérence visuelle parfaite, nous avons créé des composants réutilisables dans `Ui/Components/`.

### Pourquoi ?
- **Principe DRY (Don't Repeat Yourself)** : On ne répète pas le code. La carte d'un terrain (`FieldCard`) est la même dans l'écran d'accueil et dans l'écran de recherche.
- **Maintenance facile** : Si tu veux changer l'arrondi de toutes les cartes de l'app, tu ne modifies qu'un seul fichier (`FieldComponents.kt`) et TOUTE l'application est mise à jour instantanément.
- **Modularité** : Tu peux tester chaque composant indépendamment dans les "Previews" d'Android Studio.

---

## 🗺 5. Navigation Moderne (NavHost)

Tout le contrôle de navigation se trouve dans `MainActivity.kt`. C'est le chef d'orchestre.

### Concepts clés :
- **Routes** : Chaque écran a une adresse textuelle unique (ex: `"home"`, `"search"`, `"profile"`).
- **Arguments de Route** : Nous passons des données via l'URL (ex: `sport_selection/{name}`). Cela permet à l'écran suivant de savoir qui l'appelle.
- **Bottom Navigation** : La barre du bas (`AppBottomNavigationBar`) est liée aux routes du `NavHost`. Cliquer sur un icône déclenche un changement de route de manière fluide.

---

## 🛠 6. Logique Métier et Expérience Utilisateur (UX)

### Masque de saisie de Date
Dans `UserInfosViewModel`, nous avons implémenté un masque automatique :
- L'utilisateur tape juste les chiffres (ex: `12051998`).
- Le ViewModel ajoute les slashs en temps réel (`12/05/1998`).
- **Pourquoi ?** Pour garantir que la donnée enregistrée est toujours valide et éviter que l'utilisateur ne se trompe de format.

### Validation Dynamique
Le bouton "Continuer" n'est activé que si les conditions de validité sont remplies (champs non vides, date complète). Cela guide l'utilisateur et évite l'envoi de formulaires invalides.

---

## 📁 7. Structure des Fichiers et Dossiers

- `Ui/Screen` : Les "pages" complètes de ton application.
- `Ui/Components` : Les "petites briques" (boutons, cartes, barres de navigation).
- `ViewModel` : La logique qui décide quoi afficher.
- `data/Model` : La définition de tes objets (User, Field, Reservation).
- `data/Repository` : Le stockage temporaire ou permanent des données partagées.
- `gradle/libs.versions.toml` : Le catalogue centralisé de toutes les versions de ton projet. C'est beaucoup plus propre que de disperser les versions partout.

---

## 🚀 Prochaines Étapes pour ton apprentissage :

1.  **Vraie Map** : Intégrer `Google Maps` via la librairie `maps-compose`. Il faudra créer une clé API sur la Google Cloud Console.
2.  **Images Réelles** : Utiliser la bibliothèque **Coil** (`AsyncImage`) pour charger les photos des stades depuis internet (URLs).
3.  **Base de données Room** : Apprendre à sauvegarder les données sur le disque du téléphone pour qu'elles restent là même après avoir éteint l'appareil.
4.  **Backend/Firebase** : Pour permettre de vraies réservations synchronisées entre plusieurs utilisateurs en temps réel.
