# HealNote V2 - Documentation Complète

## 📋 Table des matières

1. [Installation](#installation)
2. [Fonctionnalités](#fonctionnalités)
3. [Objets Spéciaux](#objets-spéciaux)
4. [Recettes](#recettes)
5. [Mécaniques](#mécaniques)
6. [Règles](#règles)
7. [Commandes](#commandes)

---

## 🚀 Installation

### Prérequis
- Serveur Paper 1.20+
- Java 17+
- Maven (pour compiler)

### Étapes d'installation

1. **Cloner le repository**
   ```bash
   git clone https://github.com/merrylroblox-cell/plugin.git
   cd plugin
   ```

2. **Compiler le plugin**
   ```bash
   mvn clean package
   ```

3. **Copier le JAR**
   ```bash
   cp target/healnote-v2-2.0.0.jar /path/to/server/plugins/
   ```

4. **Redémarrer le serveur**
   ```bash
   ./start.sh
   ```

---

## ✨ Fonctionnalités

### 1. Death Note
Condamne un joueur à mort après un délai spécifique.

**Effets pendant la condamnation:**
- Aveuglement et Nausée constants
- Éclairs réguliers
- Battements de cœur sonores
- Dégâts progressifs dans les 30 dernières secondes
- Barre d'action affichant le temps restant

**À l'expiration:**
- Le joueur meurt
- Il est automatiquement banni
- Les Totems d'immortalité sont ignorés

### 2. Heal Note
Sauve un joueur condamné au prix de la vie du sauveur.

**Mécanique:**
- Sauve le joueur condamné
- Le sauveur meurt et est banni
- Le Heal Note est consommé

### 3. Deban Book
Lance un Défi du Destin pour débannir un joueur.

**Mécanique:**
- Invoque le Gardien du Destin (Warden amélioré)
- 45 minutes pour le vaincre
- Victoire = débannissement
- Défaite = ban permanent

### 4. Page Maudite
Ingrédient de craft pour les livres spéciaux.

### 5. Gardien du Destin
Boss Warden amélioré.

**Statistiques:**
- 384 HP (6× normal)
- 32 dégâts (4× normal)
- Vitesse augmentée
- Nom personnalisé

---

## 📦 Objets Spéciaux

### Death Note
- **Type:** Livre et plume
- **Nom:** `Death Note` (en rouge)
- **Utilisation:** Clic droit pour ouvrir

### Heal Note
- **Type:** Livre et plume
- **Nom:** `Heal Note` (en vert)
- **Utilisation:** Clic droit pour ouvrir

### Deban Book
- **Type:** Livre et plume
- **Nom:** `Deban Book` (en violet)
- **Utilisation:** Clic droit pour ouvrir

### Page Maudite
- **Type:** Papier
- **Nom:** `Page Maudite` (en doré)
- **Utilisation:** Ingrédient de craft

---

## 🔨 Recettes

### Page Maudite
```
Nether Star | Deepslate renforcée | Nether Star
Deepslate renforcée | Papier | Deepslate renforcée
Nether Star | Deepslate renforcée | Nether Star
```

### Death Note
```
Totem | Page Maudite | Totem
Page Maudite | Livre et plume | Page Maudite
Totem | Page Maudite | Totem
```

### Heal Note
```
Page Maudite | Totem | Page Maudite
Totem | Livre et plume | Totem
Page Maudite | Totem | Page Maudite
```

### Deban Book
```
Nether Star | Deepslate renforcée | Nether Star
Deepslate renforcée | Livre et plume | Deepslate renforcée
Nether Star | Deepslate renforcée | Nether Star
```

---

## 🎮 Mécaniques

### Utilisation de Death Note

1. Clic droit avec le Death Note
2. Le livre s'ouvre
3. Écrivez au format:
   ```
   Nom: [NOM_DU_JOUEUR]
   Temps: [DURÉE]
   ```
   Exemples de durée: `30s`, `10m`, `1h`

4. Signez le livre
5. Vérifications:
   - Le joueur existe et est connecté ✓
   - Le temps est entre 3s et 1h ✓
   - Le joueur n'est pas déjà condamné ✓
   - Le joueur n'a pas de Défi du Destin actif ✓

6. Si valide:
   - Death Note consommée
   - Condamnation commence
   - Annonce serveur
   - Sons inquiétants

### Utilisation de Heal Note

1. Clic droit avec le Heal Note
2. Le livre s'ouvre
3. Écrivez:
   ```
   Nom: [NOM_DU_JOUEUR_CONDAMNÉ]
   ```

4. Signez le livre
5. Vérification:
   - Le joueur est réellement condamné ✓

6. Si valide:
   - Joueur condamné sauvé
   - Sauveur meurt et est banni
   - Heal Note consommée
   - Particules vertes
   - Annonce serveur

### Utilisation de Deban Book

1. Clic droit avec le Deban Book
2. Le livre s'ouvre
3. Écrivez:
   ```
   Nom: [NOM_DU_JOUEUR_BANNI]
   ```

4. Signez le livre
5. Vérification:
   - Le joueur est réellement banni ✓

6. Si valide:
   - Deban Book consommé
   - Gardien du Destin invoqué
   - Défi lancé (45 minutes)
   - Particules violettes

### Défi du Destin

**Conditions de Victoire:**
- Vaincre le Gardien du Destin
- Le joueur banni est débanni automatiquement
- Annonce au serveur

**Conditions de Défaite:**
- 45 minutes écoulées
- Le Gardien disparaît
- Le joueur reste banni définitivement
- Annonce au serveur

---

## ⚙️ Règles

✅ **Rules de HealNote V2**

1. Un joueur ne peut pas être condamné deux fois
2. Un joueur déjà condamné ne peut pas recevoir une nouvelle Death Note
3. Lors d'un Défi du Destin actif, aucune Death Note ne peut viser le joueur
   - Tentative = Death Note non consommée
4. Le Deban Book est le seul moyen officiel de débannir un joueur
5. Les Totems d'immortalité ne fonctionnent JAMAIS contre une Death Note
6. Les bans de HealNote V2 sont permanents (base de données)
7. Un joueur ne peut avoir qu'un seul Défi du Destin actif

---

## 📝 Commandes

### Administration

```bash
# Informations du plugin
/healnote info

# Lister les joueurs condamnés
/healnote condemned

# Lister les joueurs bannis
/healnote banned

# Lister les défis actifs
/healnote challenges

# Débannir manuellement
/healnote unban <nom>

# Retirer une condamnation
/healnote removecondemn <nom>
```

---

## 🎵 Sons

### Death Note
- **Validation:** Bruit inquiétant (Warden Hurt)
- **Pendant:** Battements de cœur (Warden Heartbeat)
- **Exécution:** Son du Warden

### Heal Note
- **Effet:** Sons doux et apaisants (Player Levelup)

### Deban Book
- **Invocation:** Son épique (Warden Emerge)

---

## 🎨 Particules

### Death Note
- Particules rouges
- Éclairs

### Heal Note
- Particules vertes (Happy Villager)

### Deban Book
- Particules violettes (Soul)
- Éclairs

---

## 📊 État du Projet

**Version:** 2.0.0
**Statut:** Beta
**Dernière mise à jour:** 2024

### À venir
- [ ] PersistentDataContainer pour identifier les livres
- [ ] Système de configuration
- [ ] Sauvegarde des données permanentes
- [ ] Interface graphique d'administration
- [ ] Logs détaillés

---

## 🤝 Support

Pour toute question ou bug, veuillez créer une issue sur GitHub.

---

**Développé avec ❤️ pour les serveurs Minecraft**
