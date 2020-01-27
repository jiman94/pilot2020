package oss.pilot.jwt.controller;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import oss.pilot.jwt.model.AuthDetail;
import oss.pilot.jwt.model.AuthenticationRequest;
import oss.pilot.jwt.model.AuthenticationResponse;
import oss.pilot.jwt.model.JwtUser;
import oss.pilot.jwt.model.UserVo;
import oss.pilot.jwt.service.MyUserDetailsService;
import oss.pilot.jwt.util.JwtUtil;

@RestController
@Slf4j 
public class JwtTokenController {

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private MyUserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	

	@RequestMapping({ "/hello" })
	public String firstPage() {
		
		log.debug("<<<<<<<<<<<<<<<<<<<<<<");
		
		return "Hello World";
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

		try {
			
			log.debug("<<<<<<<<<<<<<<<<<<<<<<");
			
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}


		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

	

	// 서버가 정상적이라는 login page 용 rest
	@GetMapping(value = "/aa", produces = "application/json")
	public ResponseEntity<Void> initToken(HttpServletRequest req) {
		
		log.debug("<<<<<<<<<<<<<<<<<<<<<<"+req.toString());
		
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = "/bb")
	public ResponseEntity<Void> bb(HttpServletRequest req) {
		
		log.debug("<<<<<<<<<<<<<<<<<<<<<<"+req.toString());
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthDetail> register(@RequestBody UserVo userVo, HttpServletRequest req)
			throws AuthenticationException {
		
		try {
			
			log.debug("<<<<<<<<<<<<<<<<<<<<<<"+ userVo.toString());
			
			ResponseEntity<AuthDetail> loginResult = null;
			
		/*	
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userVo.getUserid(), userVo.getUserpw());
			
			authToken.setDetails(new WebAuthenticationDetails(req));
			
			log.debug("<<<<<<<<<<<<<<<<<<<<<<"+authToken.toString());
			
			Authentication authentication = authenticationManager.authenticate(authToken);
			
		
			
			JwtUser jwtUser = new JwtUser();
			log.debug("<<<<<<<<<<<<<<<<<<<<<<"+jwtUser.toString());
			
			jwtUser.setUserid(userVo.getUserid());
			
	//		JwtUser jwtUserData = jwtService.getUserInfo(jwtUser);
		//	jwtUserData.setLoginOk(true);
			
		//	Date expireDate = DateUtils.getAddHours(new Date(), loginDuration);
//			Date expireTimestamp = new Timestamp(expireDate.getTime());
	//		jwtUser.setExpireDateTime(DateUtils.getTimsStampString(expireTimestamp));

			SecurityContextHolder.getContext().setAuthentication(authentication);

*/
			long iat = System.currentTimeMillis();

			AuthDetail authDetail = null;//jwtTokenUtil.generateToken(authentication, jwtUserData);

			//log.debug("<<<<<<<<<<<<<<<<<<<<<<"+authDetail.toString());
			
			//authDetail.setServerAccessTime(String.valueOf(iat));
			loginResult = new ResponseEntity<>(authDetail, HttpStatus.OK);
			
			
			log.debug("<<<<<<<<<<<<<<<<<<<<<< end ");
			
			
			return loginResult == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : loginResult;

		} catch (BadCredentialsException e) {
//			logger.error("##### ##### ##### JwtTokenController - register error: BadCredentialsException - {}", e.getMessage());
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			
		} catch (Exception e) {
//			logger.error("##### ##### ##### JwtTokenController - register error: Exception - {}", e.getMessage());
			return null;
		}

	}

}
