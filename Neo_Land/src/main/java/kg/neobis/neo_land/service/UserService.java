package kg.neobis.neo_land.service;

import kg.neobis.neo_land.dto.RegistrationUserDto;
import kg.neobis.neo_land.dto.UserDto;
import kg.neobis.neo_land.mapper.RegistrationUserMapper;
import kg.neobis.neo_land.entity.User;
import kg.neobis.neo_land.exception.RecordNotFoundException;
import kg.neobis.neo_land.mapper.UserMapper;
import kg.neobis.neo_land.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RegistrationUserMapper regUserMapper;
    private final UserMapper userMapper;

    private PasswordEncoder passwordEncoder;
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                "Пользователь не найден"
        ));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }

//    public User createNewUser(RegistrationUserDto registrationUserDto) {
//        User user = regUserMapper.toEntity(registrationUserDto);
//        return userRepository.save(user);
//    }

    public User createNewUser(RegistrationUserDto registrationUserDto) {
        User user = regUserMapper.toEntity(registrationUserDto);
        String rawPassword = registrationUserDto.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Пользователь не найден!"));
        UserDto userDto = userMapper.toModel(user);
        return userDto;
    }

    public List<UserDto> findAllUser() {
        List<User> userList = userRepository.findAll();
        if(userList.isEmpty()) {
            throw new RecordNotFoundException("Список пользователей пуст");
        }
        List<UserDto> userDtoList = userMapper.toModelList(userList);
        return userDtoList;
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Пользователь не найден!"));
        userRepository.deleteById(user.getId());
    }
}
