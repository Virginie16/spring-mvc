package fr.afpa.mvc.mvccontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.afpa.mvc.data.Notes;
import fr.afpa.mvc.model.Note;

import java.util.List;

/**
 * Le {@link NoteController} est un contrôleur d'application Spring MVC qui alimente une interface utilisateur
 * permettant d'afficher et d'ajouter des notes. 
 * 
 * Il utilise Thymeleaf pour implémenter l'interface utilisateur et se connecte au stockage de données en mémoire fourni par la classe {@link Notes}.
 * 
 * L'URL premettant d'accéder aux pages est `/notes`.
 * Pour afficher la liste des notes, le contrôleur suit l'algorithme suivant :
 * 1. récupèration de la liste depuis le stockage en mémoire
 * 2. ajout en tant que champ classe
 * 3. passage en attribut à {@link org.springframework.ui.Model},
 * 4. renvoie une vue en fonction de ce qui est demandé
 * 
 * Pour ajouter une nouvelle note, le contrôleur traite la requête POST envoyée par le formulaire HTML, récupère les paramètres `title` et `text` de la requête, crée une nouvelle note avec ces paramètres et l'ajoute dans le stockage. Ensuite, il redirige la requête vers la page des notes.
 */
@Controller
@RequestMapping("/notes")
public class NoteController {
    private final Notes notes;

    public NoteController(Notes notes) {
        this.notes = notes;
    }

    /**
     *  Implémenter une méthode permettant de répondre à des requêtes HTTP "GET".
     * Cette méthode devra :
     * 1. récupérer les notes disponibles dans la classe "Notes"
     * 2. modifier l'objet de la classe "Model" permettant de communiquer des données à la vue
     * 3. retourner la vue nommées "notes"
     * 
     * La vue "notes" fait référence au template nommé "notes.html" situé dans le dossier "src\main\resources\templates"
     * 
     * Pour vous aider, un exemple de méthode de controller : https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller.html
     */
    @GetMapping
    public String getNotes(Model model) {
        // 1. Récupérer les notes disponibles dans la classe "Notes"
        List<Note> noteList = notes.getAll();  // Suppose que Notes a une méthode getAllNotes()

        // 2. Ajouter la liste des notes à l'objet Model
        model.addAttribute("noteList", noteList);

        // 3. Retourner la vue nommée "notes"
        return "notes";  // Le nom de la vue est "notes", qui correspond à "notes.html"
    }


    /**
     *  Compléter la méthode ci-dessous pour gérer la création d'une note via une requête "POST".
     *  Analyser la requête http effectuée lors de la création de la note. Que contient le "body" ?
     * 
     * @param note La note à créer, les données proviennent du "body" de la requête http
     * @return Le nom de la vue à rendre
     */
    @PostMapping
    public String addNote(Note note) {
        //  ajouter la nouvelle note à la collection
        // 1. Ajouter la nouvelle note à la collection
        notes.add(note);
        //  effectuer une redirection vers la vue "notes" pour rafraichir la page
        // Documentation -> https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-methods/redirecting-passing-data.html
        // 2. Rediriger vers la vue "notes" pour rafraichir la page
        return "redirect:/notes";
    }
}
