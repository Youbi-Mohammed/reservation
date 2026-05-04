# Reservation App

Application Android moderne de réservation de terrains de sport.

## Aperçu
Cette application permet aux utilisateurs de :
- Créer un profil utilisateur.
- Sélectionner un sport préféré.
- Rechercher des terrains sur une carte ou via une liste.
- Gérer leurs réservations.

## Stack Technique
- **UI** : Jetpack Compose (Material 3)
- **Architecture** : MVVM + Repository Pattern
- **Navigation** : Compose Navigation
- **Langage** : Kotlin 2.1.0

## Structure
- `app/src/main/java/com/example/reservation/`
    - `Ui/Screen` : Écrans (Home, Search, Profile, etc.)
    - `Ui/Components` : Composants réutilisables (Cards, BottomBar)
    - `ViewModel` : Logique de présentation
    - `data/Model` : Modèles de données
    - `data/Repository` : Persistance en mémoire
