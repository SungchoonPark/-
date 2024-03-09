package ch04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class UserRegisterTest {
    private UserRegister userRegister;
    private WeakPasswordChecker mockWeakPasswordChecker = Mockito.mock(WeakPasswordChecker.class);
    private MemoryUserRepository fakeRepository = new MemoryUserRepository();
    private EmailNotifier mockEmailNotifier = Mockito.mock(EmailNotifier.class);

    @BeforeEach
    void setUp() {
        userRegister = new UserRegister(mockWeakPasswordChecker, fakeRepository, mockEmailNotifier);
    }

    @Test
    @DisplayName("약한 암호면 가입 실패")
    void weakPassword() {
        BDDMockito.given(mockWeakPasswordChecker.checkPasswordWeak("pw")).willReturn(true);

        assertThrows(WeakPasswordException.class, () -> {
            userRegister.register("id", "pw", "email");
        });
    }

    @Test
    @DisplayName("회원가입시 암호 검사 수행함")
    void checkPassword() {
        userRegister.register("id", "pwd", "email");

        BDDMockito.then(mockWeakPasswordChecker)
                .should()
                .checkPasswordWeak(BDDMockito.anyString());
    }

    @Test
    @DisplayName("이미 같은 ID가 존재하면 가입 실패")
    void dupleIDExists() {
        //이미 같은 ID 존재하는 상황 만들기
        fakeRepository.save(new User("id", "pw1", "email@email.com"));

        assertThrows(DupleIdException.class, () -> {
            userRegister.register("id", "pw2", "email");
        });
    }

    @Test
    @DisplayName("같은 ID가 없으면 가입 성공")
    void noDupleIdExists() {
        userRegister.register("id", "pwd", "email");

        User user = fakeRepository.findById("id");

        assertEquals("email", user.getEmail());
        assertEquals("id", user.getId());
    }

    @Test
    @DisplayName("가입하면 메일을 전송함")
    void whenRegisterThenSendMail() {
        userRegister.register("id", "pwd", "email@email.com");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        BDDMockito.then(mockEmailNotifier)
                        .should().sendRegisterEmail(captor.capture());

        String realEmail = captor.getValue();
        assertEquals("email@email.com", realEmail);
    }
}
