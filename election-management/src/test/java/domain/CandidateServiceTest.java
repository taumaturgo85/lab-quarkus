package domain;

import io.quarkus.test.junit.QuarkusTest;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@QuarkusTest
class CandidateServiceTest {

    @InjectMocks
    CandidateService service;

    @Mock
    CandidateRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save() {
        Candidate candidate = Instancio.create(Candidate.class);
        service.save(candidate);

        verify(repository).save(candidate);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findAll() {
        var candidates = Instancio.stream(Candidate.class).limit(10).toList();

        when(repository.findAll()).thenReturn(candidates);

        var result = service.findAll();

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);

        assertEquals(result, candidates);
    }

    @Test
    void findById_whenCandidateIsFound_returnsCandidate() {
        var domain = Instancio.create(Candidate.class);

        when(repository.findById(domain.id())).thenReturn(Optional.of(domain));

        var result = service.findById(domain.id());

        verify(repository).findById(domain.id());
        verifyNoMoreInteractions(repository);

        assertEquals(result, domain);
    }

    @Test
    void findById_whenCandidateIsNotFound_throwsException() {
        var id = UUID.randomUUID().toString();
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> service.findById(id));
        verify(repository).findById(id);
        verifyNoMoreInteractions(repository);
    }
}
