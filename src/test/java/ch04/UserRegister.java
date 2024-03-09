package ch04;

public class UserRegister {
    private WeakPasswordChecker weakPasswordChecker;
    private UserRepository userRepository;
    private EmailNotifier emailNotifier;

    public UserRegister(WeakPasswordChecker weakPasswordChecker, UserRepository userRepository, EmailNotifier emailNotifier) {
        this.weakPasswordChecker = weakPasswordChecker;
        this.userRepository = userRepository;
        this.emailNotifier = emailNotifier;
    }

    public void register(String id, String pwd, String email) {
        if (weakPasswordChecker.checkPasswordWeak(pwd)) {
            throw new WeakPasswordException();
        }
        User user = userRepository.findById(id);
        if (user != null) {
            throw new DupleIdException();
        }
        userRepository.save(new User(id, pwd, email));

        emailNotifier.sendRegisterEmail(email);
    }
}
