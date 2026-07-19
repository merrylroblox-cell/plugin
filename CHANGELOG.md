# HealNote V2 - Changelog

## [2.0.0] - 2024

### ✨ Ajoutés
- **Death Note** - Système de condamnation à mort
  - Sélection du joueur et durée
  - Effets progressifs (aveuglement, nausée)
  - Dégâts dans les 30 dernières secondes
  - Ban automatique à l'expiration
  - Sons et particules

- **Heal Note** - Système de sauvetage
  - Sauve un joueur condamné
  - Le sauveur meurt et est banni
  - Particules vertes et sons apaisants

- **Deban Book** - Système de défi du destin
  - Invoque un Gardien du Destin amélioré
  - 45 minutes pour le vaincre
  - Débannissement automatique en cas de victoire
  - Ban permanent en cas de défaite

- **Page Maudite** - Ingrédient de craft
  - Recette complexe
  - Ingrédient pour tous les livres spéciaux

- **Gardien du Destin** - Boss Warden
  - 384 HP (6× normal)
  - 32 dégâts (4× normal)
  - Vitesse augmentée
  - Nom personnalisé

- **Managers**
  - CondemnationManager - Gestion des condamnations
  - BanManager - Gestion des bans permanents
  - FateChallengeManager - Gestion des défis
  - BookManager - Identification des livres
  - CraftManager - Gestion des recettes

- **Listeners**
  - DeathNoteListener - Interactions Death Note
  - HealNoteListener - Interactions Heal Note
  - DebanBookListener - Interactions Deban Book
  - FateChallengeListener - Événements des défis
  - LoginListener - Vérification des bans
  - TotemListener - Prévention des Totems

- **Utilités**
  - Logger - Logging centralisé
  - EffectUtil - Effets visuels et sonores

### 🎨 Features
- Système de reconnaissance des livres par nom
- Barre d'action affichant le temps restant
- Annonces serveur formatées
- Sons inquiétants et épiques
- Particules colorées (rouge, vert, violet)
- Éclairs et tempêtes

### 📋 Règles Implémentées
- Un joueur ne peut pas être condamné deux fois
- Les Totems d'immortalité ne fonctionnent pas avec Death Note
- Le Deban Book est le seul moyen de débannir officiellement
- Bans permanents et gérés localement

## À Venir

### v2.1
- [ ] PersistentDataContainer pour identifier les livres
- [ ] Sauvegarde des données en YAML/JSON
- [ ] Système de configuration personnalisable
- [ ] Commandes d'administration

### v2.2
- [ ] Interface graphique d'administration
- [ ] Logs détaillés des événements
- [ ] Base de données pour les bans permanents
- [ ] Webhooks Discord optionnels

### v2.3
- [ ] Système de rédemption
- [ ] Défis bonus
- [ ] Lore personnalisé pour les livres
- [ ] Animations personnalisées

---

**Version actuelle: 2.0.0 - Beta**
