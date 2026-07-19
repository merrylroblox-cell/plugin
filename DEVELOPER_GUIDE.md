# HealNote V2 - Guide de Développement

## Structure du Projet

```
healnote-v2/
├── src/main/java/com/healnote/
│   ├── HealNotePlugin.java          # Classe principale
│   ├── manager/
│   │   ├── CondemnationManager.java # Gestion des condamnations
│   │   ├── BanManager.java          # Gestion des bans
│   │   ├── FateChallengeManager.java # Gestion des défis
│   │   ├── BookManager.java         # Identification des livres
│   │   └── CraftManager.java        # Gestion des recettes
│   ├── listener/
│   │   ├── DeathNoteListener.java   # Listener Death Note
│   │   ├── HealNoteListener.java    # Listener Heal Note
│   │   ├── DebanBookListener.java   # Listener Deban Book
│   │   ├── FateChallengeListener.java # Listener Défi du Destin
│   │   ├── LoginListener.java       # Listener de connexion
│   │   └── TotemListener.java       # Listener pour Totems
│   └── util/
│       ├── Logger.java              # Logging
│       └── EffectUtil.java          # Effets visuels/sonores
├── src/main/resources/
│   └── plugin.yml                   # Configuration du plugin
├── pom.xml                          # Configuration Maven
└── README.md
```

## Architecture

### Managers

**CondemnationManager**
- Gère les condamnations actives
- Stocke les données de condamnation
- Vérifie les conditions de validité

**BanManager**
- Gère les bans permanents
- Base de données en mémoire
- Vérifie les accès des joueurs

**FateChallengeManager**
- Gère les défis du destin
- Crée et supprime les gardiens
- Gère les timers (45 minutes)

**BookManager**
- Identifie les types de livres
- Basé sur les noms personnalisés

**CraftManager**
- Enregistre les recettes de craft
- Crée les items spéciaux

### Listeners

Chaque listener gère un type d'interaction:
- **DeathNoteListener** → Condamnation
- **HealNoteListener** → Sauvetage
- **DebanBookListener** → Défi du Destin
- **FateChallengeListener** → Victoire/Défaite
- **LoginListener** → Vérification des bans
- **TotemListener** → Prévention des Totems

## Améliorations Futures

### 1. PersistentDataContainer
```java
NamespacedKey key = new NamespacedKey(plugin, "book_type");
itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "death_note");
```

### 2. Sauvegarde des Données
- YAML ou JSON
- Sauvegarde des bans permanents
- Logs des événements

### 3. Configuration
```yaml
healnote:
  condemnation:
    min_time: 3
    max_time: 3600
  challenge:
    duration: 2700
    guardian_health: 384
    guardian_damage: 32
  sounds:
    enabled: true
  particles:
    enabled: true
```

### 4. Commandes d'Administration
```java
@CommandHandler(command = "healnote")
public void handleCommand(CommandSender sender, String[] args) {
    // Implémentation
}
```

## Compilation et Déploiement

### Build
```bash
mvn clean package
```

### Résultat
```
target/healnote-v2-2.0.0.jar
```

### Déploiement
```bash
cp target/healnote-v2-2.0.0.jar /server/plugins/
```

## Dépendances

- **Paper API 1.20.1** - Framework Minecraft
- **Maven 3.8+** - Build tool
- **Java 17+** - Runtime

## Points Clés du Code

### Validation des Entrées
```java
// Vérifier que le joueur existe
Player targetPlayer = Bukkit.getPlayer(targetName);
if (targetPlayer == null || !targetPlayer.isOnline()) {
    return false;
}
```

### Gestion des Timers
```java
BukkitTask task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
    // Code qui s'exécute chaque seconde
}, 0L, 20L); // 20 ticks = 1 seconde
```

### Identification des Livres
```java
if (BookManager.isDeathNote(event.getBook())) {
    // C'est une Death Note
}
```

## Tests

### Tester Death Note
1. Crafter une Death Note
2. Ouvrir et écrire:
   ```
   Nom: [Joueur]
   Temps: 30s
   ```
3. Signer
4. Vérifier les effets

### Tester Heal Note
1. Condamner un joueur avec Death Note
2. Crafter une Heal Note
3. Ouvrir et écrire le nom du condamné
4. Signer
5. Vérifier que le sauveur meurt

### Tester Deban Book
1. Banni un joueur (Death Note ou Heal Note)
2. Crafter un Deban Book
3. Ouvrir et écrire le nom du banni
4. Signer
5. Vérifier l'apparition du Gardien

---

**Pour plus d'informations, consultez DOCUMENTATION.md**
