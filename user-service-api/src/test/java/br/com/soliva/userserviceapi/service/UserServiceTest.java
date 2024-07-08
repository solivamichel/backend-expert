package br.com.soliva.userserviceapi.service;

import br.com.soliva.userserviceapi.entity.User;
import br.com.soliva.userserviceapi.mapper.UserMapper;
import br.com.soliva.userserviceapi.repository.UserRepository;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateUserRequest;
import models.responses.UserResponse;
import org.bson.assertions.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static br.com.soliva.userserviceapi.creator.CreatorUtils.generateMock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Test
    @DisplayName("When call findById with valid id then return UserResponse")
    void whenCallFindByIdValidIdThenReturnUserResponse() {
        when(repository.findById(anyString())).thenReturn(Optional.of(new User()));
        when(mapper.fromEntity(any(User.class))).thenReturn(generateMock(UserResponse.class));

        final var response = service.findById("1");

        assertNotNull(response);
        assertEquals(UserResponse.class, response.getClass());

        verify(repository, times(1)).findById(anyString());
        verify(mapper, times(1)).fromEntity(any(User.class));
    }

    @Test
    @DisplayName("When call findById with invalid id then throw ResourceNotFoundException")
    void whenCallFindByIdInvalidIdThenThrowResourceNotFoundException() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        try {
            service.findById("1");
            Assertions.fail("ResourceNotFoundException not thrown");
        } catch (ResourceNotFoundException e) {
            assertEquals(ResourceNotFoundException.class, e.getClass());
            assertEquals("Object not found. Id: 1, Type: UserResponse", e.getMessage());
        }

        verify(repository, times(1)).findById(anyString());
        verify(mapper, never()).fromEntity(any(User.class));
    }

    @Test
    @DisplayName("When call findAll then return list of UserResponse")
    void whenCallFindAllThenReturnListOfUserResponse() {
        when(repository.findAll()).thenReturn(List.of(new User(), new User()));
        when(mapper.fromEntity(any(User.class))).thenReturn(generateMock(UserResponse.class));

        final var response = service.findAll();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(UserResponse.class, response.get(0).getClass());

        verify(repository, times(1)).findAll();
        verify(mapper, times(2)).fromEntity(any(User.class));
    }

    @Test
    @DisplayName("When call save then save user sucess")
    void whenCallSaveThenSaveUserSucess() {
        final var request = generateMock(CreateUserRequest.class);

        when(mapper.fromRequest(any())).thenReturn(new User());
        when(encoder.encode(anyString())).thenReturn("encoded");
        when(repository.save(any(User.class))).thenReturn(new User());
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());

        service.save(request);

        verify(mapper, times(1)).fromRequest(request);
        verify(encoder, times(1)).encode(request.password());

        verify(repository, times(1)).save(any(User.class));
        verify(repository, times(1)).findByEmail(request.email());
    }
}