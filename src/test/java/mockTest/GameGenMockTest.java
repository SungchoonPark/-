package mockTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.List;

public class GameGenMockTest {

    @Test
    void mockTest() {
        // Mock 객체 생성
        GameNumGen genMock = Mockito.mock(GameNumGen.class);

        // 스텁 설정 willReturn 은 해당 파라미터로 해당 메서드를 실행하면 지정한 값을 return 해줌
        BDDMockito.given(genMock.generate(GameLevel.EASY)).willReturn("123");

        // 스텁 설정에 매칭되는 메서드 실행
        String num = genMock.generate(GameLevel.EASY);
        Assertions.assertEquals("123", num);
    }

    @Test
    void mockThrowTest() {
        GameNumGen genMock = Mockito.mock(GameNumGen.class);

        BDDMockito.given(genMock.generate(null)).willThrow(IllegalArgumentException.class);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            genMock.generate(null);
        });
    }

    @Test
    @DisplayName("리턴 타입이 void인 메서드의 익셉션 발생")
    void voidMethodWillThrowTest() {
        List<String> mockList = Mockito.mock(List.class);

        BDDMockito.willThrow(UnsupportedOperationException.class)
                .given(mockList)
                .clear();

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            mockList.clear();
        });

    }
}
