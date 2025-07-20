package fr.afpa.mvc.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.afpa.mvc.data.Notes;
import fr.afpa.mvc.model.Note;
import jakarta.validation.Valid;

/**
 * Implémentez le controller de l'API REST de gestion des notes
 * Ce contrôleur fournit une API REST minimaliste pour la gestion des objts "Note".
 * Il implémente deux "endpoints" qui vont permettre au client de :
 * - récupérer toutes les notes de la collection -> requête HTTP GET
 * - créer un nouvelle note -> requête HTTP POST
 * 
 * L'URL est "/api/notes"
 * 
 * Une la classe développée vous pourrez tester les requêtes http en utilisant un outil tel que :
 * - Insomnia (https://insomnia.rest/)
 * - Postman (https://www.postman.com/)
 * 
 * Pour apprendre à créer des endpoints d'une REST API en utilisant Springboot, vous pouvez utiliser la ressource suivant :
 * - https://medium.com/@abhinavv.singh/building-restful-apis-with-spring-boot-a-step-by-step-tutorial-with-detailed-code-e7e3372ae756
 **/


@RestController
@RequestMapping("/api/notes")
public class NoteRestController {

    private final Notes notes;

    /**
     * Constructeur avec 1 paramètre "notes".
     * @param notes Java bean auto-injecté
     */
    @Autowired
    public NoteRestController(Notes notes) {
        this.notes = notes;
    }

    /**
     * TODO implémentez la méthode permettant de renvoyer un JSON contenant les informations de toutes les notes
     *
     * Quelques explications -> https://www.geeksforgeeks.org/spring-postmapping-and-getmapping-annotation/
     */

    @GetMapping
    public List<Note> getNotes() {
        // Récupérer les notes disponibles dans la classe "Notes"
        List<Note> noteList = notes.getAll();  // Suppose que Notes a une méthode getAll()

        // Retourner la liste des notes
        return noteList;
    }

    /**
     * la version ci-dessous est erronée car elle ne correspond pas à l'attendu du test
     */
//     /**
//     * Implémentez la méthode permettant de renvoyer un JSON contenant les informations de toutes les notes
//     *
//     * Quelques explications -> https://www.geeksforgeeks.org/spring-postmapping-and-getmapping-annotation/
//     */
//    /**
//     * Récupérer toutes les notes.
//     * @return ResponseEntity contenant la liste des notes et un code HTTP 200
//     */
//    @GetMapping
//    public ResponseEntity<List<Note>> getNotes() {
//        // 1. Récupérer les notes disponibles dans la classe "Notes"
//        List<Note> noteList = notes.getAll();  // Suppose que Notes a une méthode getAllNotes()
//
//        // Vérifier si la liste est vide
//        if (noteList.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Code HTTP 204 si aucune note n'est disponible
//        }
//
//        // Retourner les notes avec un code HTTP 200
//        return new ResponseEntity<>(noteList, HttpStatus.OK);
//    }


    /**
     * Implémentez la méthode permettant de créer une nouvelle note.
     * Cette méthode doit répondre à une requête http POST 
     * 
     * Attention, cas particulier : s'il manque le titre ou le texte de la note il faudra renvoyer une erreur 400 (BAD REQUEST)
     * 
     * Quelques explications -> https://www.geeksforgeeks.org/spring-postmapping-and-getmapping-annotation/
     */
    /**
     * Créer une nouvelle note.
     * @param note L'objet Note passé en JSON dans le corps de la requête
     * @return ResponseEntity contenant la note créée et un code HTTP 201
     */
    @PostMapping
    public ResponseEntity<Void> addNote(@Valid @RequestBody Note note) {
        // Vérification si les champs nécessaires sont présents (validés grâce à @Valid)
        if (note.getTitle() == null || note.getText() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Code HTTP 400 si le titre ou le texte est manquant
        }

        // Sauvegarder la note
        notes.add(note);  // Suppose que Notes a une méthode save(Note note)

        // Retourner la note créée avec un code HTTP 201 (Created)
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
