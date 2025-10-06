package za.co.shyftit.capitectransactionaggregator.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.GrantedAuthority;
import za.co.shyftit.capitectransactionaggregator.models.User;

import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SecurityUtils {
    private final static String JWT_ACCESS_SECRET = System.getenv("TRANSACTIONS_AGGREGATOR_SECRET_KEY");
    private final static Long JWT_ACCESS_ACTIVE_DURATION = (long) Integer.parseUnsignedInt(System.getenv("JWT_ACCESS_ACTIVE_DURATION"));

    public static Map<String, String> getAccessTokenInfo(User user, HttpServletRequest request)
    {
        Algorithm algorithm = Algorithm.HMAC256(JWT_ACCESS_SECRET.getBytes());
        var expiryDate = Instant.now().plus(Duration.ofMillis(JWT_ACCESS_ACTIVE_DURATION));
        var expiresAt = expiryDate.toEpochMilli();
        var accessToken =  JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expiryDate)
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        var tokenInfo = new HashMap<String, String>();
        tokenInfo.put("access_token", accessToken);
        tokenInfo.put("expires_at", String.valueOf(expiresAt));
        return tokenInfo;
    }

    public static String getRefreshToken(User user, HttpServletRequest request)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        var expiryDate = calendar.getTime();

        Algorithm algorithm = Algorithm.HMAC256(JWT_ACCESS_SECRET.getBytes());
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expiryDate)
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }
}
