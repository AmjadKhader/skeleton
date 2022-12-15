package example.skeleton.filter;

import example.skeleton.configuration.ApplicationConfiguration;
import example.skeleton.configuration.AuthenticationEndpointConfiguration;
import example.skeleton.utils.TokenParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Order(1)
public class AuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private ApplicationConfiguration applicationConfiguration;

    public AuthenticationFilter(ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestMethod = request.getMethod();
        String requestURI = request.getRequestURI();

        List<AuthenticationEndpointConfiguration> currentEndpoint = applicationConfiguration.getCheckAuthentication()
                .stream()
                .filter(endpoint -> endpoint.getMethod().equalsIgnoreCase(requestMethod) && requestURI.contains(endpoint.getPath()))
                .collect(Collectors.toList());

        if (currentEndpoint.size() == 0) {
            filterChain.doFilter(request, response); // no need to check auth on this endpoint ...
            return;
        }

        if (currentEndpoint.size() > 1) {
            response.setStatus(405, "Error configure auth endpoints");
            return;
        }


        String jwtToken = request.getHeader("Authorization");
        if (TokenParser.validateToken(jwtToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        response.setStatus(401);
    }
}
