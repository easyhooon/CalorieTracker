package com.kenshi.core.domain.use_case

// usecase 로 비즈니스 로직을 뺌으로써 뷰모델에서 반복되서 쓰일 함수들을 모듈화할 수 있고 재활용할 수 있다.
// 테스트도 물론
// 물론 각각의 usecase 역시 hilt 를 통해 주입해줘야 사용 가능함
class FilterOutDigits {

    // 클래스를 함수처럼 실행시키기 위함
    operator fun invoke(text: String): String {
        return text.filter { it.isDigit() }
    }
}