apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    // Flow 반환형을 사용하기 위해 coroutine 의존성 추가
    "implementation"(Coroutines.coroutines)
}