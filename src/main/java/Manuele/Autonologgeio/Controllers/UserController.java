package Manuele.Autonologgeio.Controllers;

import Manuele.Autonologgeio.Entities.User;
import Manuele.Autonologgeio.Payloads.ProfiloDTO;
import Manuele.Autonologgeio.Service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@OpenAPIDefinition(
        security = {@SecurityRequirement(name = "bearerAuth")}
)
public class UserController {
    @Autowired
    private UserService usersService;


    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public Page<User> getAllUser(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String orderBy) {
        return usersService.getAllUser(page, size, orderBy);
    }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('USER')")
    public UserDetails getProfile(@AuthenticationPrincipal UserDetails currentUser) {
        return currentUser;
    }

    ;

    @GetMapping("/profilo")
    @PreAuthorize("hasAuthority('USER')")
    public ProfiloDTO getProfileComplete(@AuthenticationPrincipal UserDetails currentUser) throws Exception {
        return usersService.getProfileComplete(currentUser);

    }

    ;


    @DeleteMapping("/me")
    @SecurityRequirement(name = "bearerAuth")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void deleteProfile(@AuthenticationPrincipal User currentUser) {
        usersService.findByIdAndDelete(currentUser.getId());
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public User findById(@PathVariable int id) {
        return usersService.findById(id);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void findByIdAndDelete(@PathVariable int id) {
        usersService.findByIdAndDelete(id);
    }


}
