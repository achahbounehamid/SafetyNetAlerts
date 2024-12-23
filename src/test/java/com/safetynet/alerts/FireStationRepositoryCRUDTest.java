
package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.FireStationCRUD;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe {@link FireStationRepositoryCRUD}.
 * Cette classe teste toutes les méthodes CRUD du repository pour garantir leur bon fonctionnement.
 */
class FireStationRepositoryCRUDTest {

    private FireStationRepositoryCRUD repository;

    /**
     * Initialisation avant chaque test.
     * Crée une nouvelle instance du repository pour assurer un environnement propre.
     */
    @BeforeEach
    void setUp() {
        repository = new FireStationRepositoryCRUD();
    }

    /**
     * Teste la méthode {@link FireStationRepositoryCRUD#save(FireStationCRUD)}
     * pour l'ajout d'une nouvelle station.
     */
    @Test
    void testSave_NewStation() {
        // Arrange
        FireStationCRUD fireStation = new FireStationCRUD("123 Main St", 1);

        // Act
        FireStationCRUD result = repository.save(fireStation);

        // Assert
        assertEquals(fireStation, result, "La station sauvegardée doit correspondre à la station ajoutée.");
        assertTrue(repository.findByAddress("123 Main St").isPresent(), "La station doit être trouvée après l'ajout.");
    }

    /**
     * Teste la méthode {@link FireStationRepositoryCRUD#save(FireStationCRUD)}
     * pour la mise à jour d'une station existante.
     */
    @Test
    void testSave_UpdateExistingStation() {
        // Arrange
        FireStationCRUD fireStation = new FireStationCRUD("123 Main St", 1);
        repository.save(fireStation);

        FireStationCRUD updatedFireStation = new FireStationCRUD("123 Main St", 2);

        // Act
        FireStationCRUD result = repository.save(updatedFireStation);

        // Assert
        assertEquals(updatedFireStation, result, "La station mise à jour doit correspondre à la nouvelle station.");
        assertEquals(1, repository.findAll().size(), "Il ne doit y avoir qu'une seule station après mise à jour.");
        assertEquals(2, repository.findByAddress("123 Main St").get().getStationNumber(),
                "Le numéro de station doit être mis à jour.");
    }

    /**
     * Teste la méthode {@link FireStationRepositoryCRUD#findByAddress(String)}
     * pour une adresse existante.
     */
    @Test
    void testFindByAddress_ExistingAddress() {
        // Arrange
        FireStationCRUD fireStation = new FireStationCRUD("123 Main St", 1);
        repository.save(fireStation);

        // Act
        Optional<FireStationCRUD> result = repository.findByAddress("123 Main St");

        // Assert
        assertTrue(result.isPresent(), "La station doit être trouvée pour une adresse existante.");
        assertEquals(fireStation, result.get(), "La station trouvée doit correspondre à celle ajoutée.");
    }

    /**
     * Teste la méthode {@link FireStationRepositoryCRUD#findByAddress(String)}
     * pour une adresse inexistante.
     */
    @Test
    void testFindByAddress_NonExistingAddress() {
        // Act
        Optional<FireStationCRUD> result = repository.findByAddress("Unknown Address");

        // Assert
        assertFalse(result.isPresent(), "Aucune station ne doit être trouvée pour une adresse inexistante.");
    }

    /**
     * Teste la méthode {@link FireStationRepositoryCRUD#updateStation(String, FireStationCRUD)}
     * pour une mise à jour réussie.
     */
    @Test
    void testUpdateStation_SuccessfulUpdate() {
        // Arrange
        FireStationCRUD fireStation = new FireStationCRUD("123 Main St", 1);
        repository.save(fireStation);

        FireStationCRUD updatedFireStation = new FireStationCRUD("456 Elm St", 2);

        // Act
        boolean result = repository.updateStation("123 Main St", updatedFireStation);

        // Assert
        assertTrue(result, "La mise à jour doit être réussie pour une station existante.");
        assertTrue(repository.findByAddress("456 Elm St").isPresent(),
                "La nouvelle adresse doit être trouvée après mise à jour.");
        assertEquals(2, repository.findByAddress("456 Elm St").get().getStationNumber(),
                "Le numéro de station doit être mis à jour.");
        assertFalse(repository.findByAddress("123 Main St").isPresent(),
                "L'ancienne adresse ne doit plus exister après mise à jour.");
    }

    /**
     * Teste la méthode {@link FireStationRepositoryCRUD#updateStation(String, FireStationCRUD)}
     * pour une mise à jour échouée (adresse inexistante).
     */
    @Test
    void testUpdateStation_AddressNotFound() {
        // Arrange
        FireStationCRUD updatedFireStation = new FireStationCRUD("456 Elm St", 2);

        // Act
        boolean result = repository.updateStation("Unknown Address", updatedFireStation);

        // Assert
        assertFalse(result, "La mise à jour doit échouer pour une adresse inexistante.");
    }

    /**
     * Teste la méthode {@link FireStationRepositoryCRUD#deleteByAddress(String)}
     * pour une suppression réussie.
     */
    @Test
    void testDeleteByAddress_SuccessfulDeletion() {
        // Arrange
        FireStationCRUD fireStation = new FireStationCRUD("123 Main St", 1);
        repository.save(fireStation);

        // Act
        boolean result = repository.deleteByAddress("123 Main St");

        // Assert
        assertTrue(result, "La suppression doit être réussie pour une adresse existante.");
        assertFalse(repository.findByAddress("123 Main St").isPresent(),
                "La station ne doit plus être trouvée après suppression.");
    }

    /**
     * Teste la méthode {@link FireStationRepositoryCRUD#deleteByAddress(String)}
     * pour une suppression échouée (adresse inexistante).
     */
    @Test
    void testDeleteByAddress_AddressNotFound() {
        // Act
        boolean result = repository.deleteByAddress("Unknown Address");

        // Assert
        assertFalse(result, "La suppression doit échouer pour une adresse inexistante.");
    }

    /**
     * Teste la méthode {@link FireStationRepositoryCRUD#findAll()}
     * pour récupérer toutes les stations enregistrées.
     */
    @Test
    void testFindAll() {
        // Arrange
        FireStationCRUD fireStation1 = new FireStationCRUD("123 Main St", 1);
        FireStationCRUD fireStation2 = new FireStationCRUD("456 Elm St", 2);
        repository.save(fireStation1);
        repository.save(fireStation2);

        // Act
        List<FireStationCRUD> result = repository.findAll();

        // Assert
        assertEquals(2, result.size(), "Le nombre total de stations doit être correct.");
        assertTrue(result.contains(fireStation1), "La liste doit contenir la première station ajoutée.");
        assertTrue(result.contains(fireStation2), "La liste doit contenir la deuxième station ajoutée.");
    }
}

