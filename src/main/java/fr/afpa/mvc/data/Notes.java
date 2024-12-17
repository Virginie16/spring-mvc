package fr.afpa.mvc.data;

import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

import fr.afpa.mvc.model.Note;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Classe représentant une collection de notes.
 * Les permettent d'intéragir avec cette collection.
 * 
 * Classe annotée @Component :
 * cette annotation fait d'elle un bean qui sera instancié et disponible dans "ApplicationContext".
 * Cette instance pourra être utilisée n'importe où via le mécanisme d'injection de dépendance.
 * 
 * Ajoutez l'annotation @Component à cette classe pour qu'elle puisse être ajoutée au "ApplicationContext" et utilisé pour l'injection de dépendance
 */
@Component
public class Notes {
    /**
     * Table de hachage qui contiendra toutes les informations concernant les notes
     */
    private final Map<UUID, Note> notesMap = new HashMap<>();

    /**
     * @return Une liste de toutes les notes disponibles.
     */
    public List<Note> getAll(){
        var noteList = new ArrayList<>(notesMap.values());
        noteList.sort(Comparator.comparing(Note::getCreatedOn));
        return noteList;
    }

    /**
     * Ajoute une note à la collection.
     * Cette méthode initialise l'identifiant unique de la note ainsi que sa date de création.
     * 
     * @param note La note à enregistrer dans la collection.
     */
    public void add(Note note) {
        // initialisation de l'identifiant unique de la note
        note.setId(UUID.randomUUID());
        // initialisation de la date de création avec la date actuelle
        note.setCreatedOn(LocalDateTime.now());
        notesMap.put(note.getId(), note);
    }

    /**
     * Sauvegarde une note dans la collection. Si l'ID de la note est déjà présent, la note est mise à jour.
     * @param note La note à sauvegarder (ajouter ou mettre à jour).
     * @return La note après l'ajout ou la mise à jour.
     */
    public Note save(Note note) {
        // Si l'ID de la note est null (c'est une nouvelle note), on l'ajoute
        if (note.getId() == null) {
            add(note);
        } else {
            // Sinon, on met à jour la note existante
            notesMap.put(note.getId(), note);
        }
        return note;
    }

    /**
     * Récupère une note par son identifiant unique (UUID).
     * @param id L'UUID de la note à rechercher.
     * @return La note correspondante ou null si elle n'existe pas.
     */
    public Note getById(UUID id) {
        return notesMap.get(id);
    }

    /**
     * Supprime une note par son identifiant unique.
     * @param id L'UUID de la note à supprimer.
     */
    public void delete(UUID id) {
        notesMap.remove(id);
    }
}
