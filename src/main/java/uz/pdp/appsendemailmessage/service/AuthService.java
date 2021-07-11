package uz.pdp.appsendemailmessage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.appsendemailmessage.entity.ApiResponse;
import uz.pdp.appsendemailmessage.entity.UserEntity;
import uz.pdp.appsendemailmessage.entity.roles.RoleName;
import uz.pdp.appsendemailmessage.model.LoginReceiveModel;
import uz.pdp.appsendemailmessage.model.RegisterReceiveModel;
import uz.pdp.appsendemailmessage.repository.RegisterRepository;
import uz.pdp.appsendemailmessage.repository.RoleRepository;
import uz.pdp.appsendemailmessage.repository.UserRepository;
import uz.pdp.appsendemailmessage.security.JwtProvider;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by
 * Sahobiddin Abbosaliyev
 * 7/10/2021
 */
@Service
public class AuthService implements UserDetailsService {
    @Autowired
    RegisterRepository registerRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;
//    private final RegisterRepository registerRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final RoleRepository roleRepository;
//    private final JavaMailSender javaMailSender;
//    private final UserRepository userRepository;
//    private final AuthenticationManager authenticationManager;
//    private final JwtProvider jwtProvider;
//
//
//    @Autowired
//    public AuthService(@Lazy RegisterRepository registerRepository,
//                       PasswordEncoder passwordEncoder,
//                       RoleRepository roleRepository,
//                       JavaMailSender javaMailSender,
//                       UserRepository userRepository,
//                       AuthenticationManager authenticationManager,
//                       JwtProvider jwtProvider) {
//        this.registerRepository = registerRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.roleRepository = roleRepository;
//        this.javaMailSender = javaMailSender;
//        this.userRepository = userRepository;
//        this.authenticationManager = authenticationManager;
//        this.jwtProvider = jwtProvider;
//    }

    public ApiResponse addUser(RegisterReceiveModel registerReceiveModel) {
        boolean existsByEmail = registerRepository.existsByEmail(registerReceiveModel.getEmail());
        if (existsByEmail) {
            return new ApiResponse("Bunday email allaqachon ro'yxatdan o'tgan", false);
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID());
        userEntity.setFirstname(registerReceiveModel.getFirstname());
        userEntity.setLastname(registerReceiveModel.getLastname());
        userEntity.setEmail(registerReceiveModel.getEmail());
        userEntity.setPassword(passwordEncoder.encode(registerReceiveModel.getPassword()));
        userEntity.setUserRoleEntity(Collections.singleton(roleRepository.findByRoleName(RoleName.USER_ROLE)));
        userEntity.setEmailCode(UUID.randomUUID().toString());
        registerRepository.save(userEntity);
        sendMail(userEntity.getEmail(), userEntity.getEmailCode());
        return new ApiResponse("Ro'yxatdan o'tdingiz, iltimos emailingizni tekshiring", true);

    }

    public ApiResponse verifyEmail(String emailCode, String email) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmailCodeAndEmail(emailCode, email);
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            userEntity.setEnabled(true);
            userEntity.setEmailCode(null);
            userRepository.save(userEntity);
            return new ApiResponse("Akkount tasdiqlandi", true);
        }
        return new ApiResponse("Akkount tasdiqlangan", false);

    }

    public Boolean sendMail(String sendingEmail, String sendingEmailCode) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("Sahobiddin");
            mailMessage.setTo(sendingEmail);
            mailMessage.setSubject("Akkountni tasdiqlash");
            mailMessage.setText("http:localhost:8080/api/auth/verifyEmail?emailCode=" + sendingEmailCode + "&email=" + sendingEmail);
            javaMailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public ApiResponse login(LoginReceiveModel loginReceiveModel) {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(loginReceiveModel.getUsername(), loginReceiveModel.getPassword());
            Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            UserEntity userEntity = (UserEntity) authenticate.getPrincipal();
            String token = jwtProvider.generationToken(loginReceiveModel.getUsername(), userEntity.getUserRoleEntity());
            return new ApiResponse("Token", true, token);
        } catch (BadCredentialsException e) {
            return new ApiResponse("Login yoki Parol xato", false);
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(username);
        if (optionalUserEntity.isPresent())
            return optionalUserEntity.get();
        throw new UsernameNotFoundException(username + " topilmadi");
    }
}
