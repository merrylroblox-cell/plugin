# Contribuer à HealNote V2

## Comment Contribuer

### 1. Fork le Repository
```bash
git clone https://github.com/ton-username/plugin.git
cd plugin
```

### 2. Créer une Branche
```bash
git checkout -b feature/ma-feature
```

### 3. Faire les Modifications
- Suivre le style de code existant
- Ajouter des commentaires
- Tester votre code

### 4. Commit et Push
```bash
git add .
git commit -m "Add: Description de la feature"
git push origin feature/ma-feature
```

### 5. Créer une Pull Request
- Décrire les changements
- Référencer les issues
- Attendre la review

## Style de Code

### Conventions de Nommage
- **Classes:** PascalCase (ex: `DeathNoteListener`)
- **Méthodes:** camelCase (ex: `onBookSign`)
- **Constantes:** UPPER_SNAKE_CASE (ex: `DEATH_NOTE_NAME`)
- **Variables:** camelCase (ex: `targetPlayer`)

### Formatage
```java
// Indentation: 4 espaces
// Longueur max ligne: 120 caractères
// Imports: Organisés et groupés

public class Example {
    private String variable;

    public void method() {
        // Code
    }
}
```

### Documentation
```java
/**
 * Description de la méthode
 * @param param1 Description du paramètre
 * @return Description du retour
 */
public void methodName(String param1) {
    // Code
}
```

## Rapporter des Bugs

### Template de Bug Report
```
Titre: [BUG] Courte description

Description:
- Comportement attendu
- Comportement actuel
- Étapes pour reproduire

Envirônnement:
- Minecraft Version: 1.20.1
- Paper Build: XXX
- Java Version: 17
```

## Demander une Feature

### Template de Feature Request
```
Titre: [FEATURE] Description courte

Problème:
Description du problème

Solution:
Description de la solution proposée

Alternatives:
Alternatives considérées
```

## Licence

Tous les contributions sont soumises à la licence du projet.

---

**Merci de contribuer à HealNote V2! 🎉**
