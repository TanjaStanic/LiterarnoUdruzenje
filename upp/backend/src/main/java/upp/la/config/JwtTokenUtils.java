package upp.la.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import upp.la.model.User;
import upp.la.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtils {

  @Value("upp-la")
  private String APP_NAME;

  @Value("upp-la")
  public String SECRET;

  @Value("5000000")
  private int EXPIRES_IN;

  @Value("x-auth")
  private String AUTH_HEADER;

  static final String AUDIENCE_WEB = "web";
  static final String AUDIENCE_MOBILE = "mobile";
  static final String AUDIENCE_TABLET = "tablet";

  private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

  @Autowired private AuthUserDetailsService authUserDetailsService;

  @Autowired private UserRepository userRepository;

  public String generateToken(String username) {
    UserDetails user = authUserDetailsService.loadUserByUsername(username);
    User u = userRepository.findByUsername(user.getUsername());
    return Jwts.builder()
        .setIssuer(APP_NAME)
        .setSubject(username)
        .setAudience(AUDIENCE_WEB)
        .setIssuedAt(new Date())
        // .claim("role", role) //postavljanje proizvoljnih podataka u telo JWT tokena
        .claim(
            "authorities",
            user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()))
        .claim("userId", u.getId())
        .signWith(SIGNATURE_ALGORITHM, SECRET)
        .compact();
  }

  public Boolean canTokenBeRefreshed(String token) {
    final Date created = this.getIssuedAtDateFromToken(token);
    return ((!(this.isTokenExpired(token)) || this.ignoreTokenExpiration(token)));
  }

  public Boolean validateToken(String token) {
    final String email = getEmailFromToken(token);
    final Date created = getIssuedAtDateFromToken(token);
    final String username = getUsername(token);

    return (email != null && created != null && username != null);
  }

  public String getEmailFromToken(String token) {
    String email;
    try {
      final Claims claims = this.getAllClaimsFromToken(token);
      email = claims.getSubject();
    } catch (Exception e) {
      email = null;
    }
    return email;
  }

  public String getUsername(String token) {
    String username;
    try {
      final Claims claims = this.getAllClaimsFromToken(token);
      username = claims.getSubject();
    } catch (Exception e) {
      username = null;
    }
    return username;
  }

  public Date getIssuedAtDateFromToken(String token) {
    Date issueAt;
    try {
      final Claims claims = this.getAllClaimsFromToken(token);
      issueAt = claims.getIssuedAt();
    } catch (Exception e) {
      issueAt = null;
    }
    return issueAt;
  }

  public String getAudienceFromToken(String token) {
    String audience;
    try {
      final Claims claims = this.getAllClaimsFromToken(token);
      audience = claims.getAudience();
    } catch (Exception e) {
      audience = null;
    }
    return audience;
  }

  public Date getExpirationDateFromToken(String token) {
    Date expiration;
    try {
      final Claims claims = this.getAllClaimsFromToken(token);
      expiration = claims.getExpiration();
    } catch (Exception e) {
      expiration = null;
    }
    return expiration;
  }

  public int getExpiredIn() {
    return EXPIRES_IN;
  }

  public String getToken(HttpServletRequest request) {
    String authHeader = getAuthHeaderFromHeader(request);

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      return authHeader.substring(7);
    }

    return null;
  }
  //Change to "x-auth" if there are errors
  public String getAuthHeaderFromHeader(HttpServletRequest request) {
    return request.getHeader(HttpHeaders.AUTHORIZATION);
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = this.getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  private Boolean ignoreTokenExpiration(String token) {
    String audience = this.getAudienceFromToken(token);
    return (audience.equals(AUDIENCE_TABLET) || audience.equals(AUDIENCE_MOBILE));
  }

  private Claims getAllClaimsFromToken(String token) {
    Claims claims;
    try {
      claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    } catch (Exception e) {
      claims = null;
    }
    return claims;
  }
}
