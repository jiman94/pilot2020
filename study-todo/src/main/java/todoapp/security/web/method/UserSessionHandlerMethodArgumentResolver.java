package todoapp.security.web.method;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import todoapp.security.UserSession;
import todoapp.security.UserSessionRepository;

// UserSession이 Principal을 구현하게 되어 이 클래스는 작동하지 않음.
public class UserSessionHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private UserSessionRepository sessionRepository;

    public UserSessionHandlerMethodArgumentResolver(UserSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return UserSession.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return sessionRepository.get();
    }

}
