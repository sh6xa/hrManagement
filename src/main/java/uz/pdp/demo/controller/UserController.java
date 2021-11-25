package uz.pdp.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.demo.payload.UserDto;
import uz.pdp.demo.payload.response.ApiResponse;
import uz.pdp.demo.security.JwtProvider;
import uz.pdp.demo.service.UserService;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtProvider jwtProvider;

    //YANGI USER(MANAGER, XODIM) QO'SHISH
//    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody UserDto userDto) throws MessagingException {
        ApiResponse apiResponse = userService.add(userDto);
        return ResponseEntity.status(apiResponse.isStatus() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    //MA'LUMOTLARNI O'ZGARTIRISH, FOYDALANUVCHILAR FAQAT O'ZLARINING MA'LUMOTLARINI O'ZGARTIRA OLADI
//    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER','ROLE_STAFF')")
    @PutMapping
    public HttpEntity<?> edit(@Valid @RequestBody UserDto userDto) throws MessagingException {
        ApiResponse apiResponse = userService.edit(userDto);
        return ResponseEntity.status(apiResponse.isStatus()?HttpStatus.OK:HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    //TOKEN BO'YICHA FOYDALANUVCHI MA'LUMOTLARINI QAYTARADI
//    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER','ROLE_STAFF')")
    @GetMapping("/me")
    public HttpEntity<?> getByToken(){
        ApiResponse apiResponse = userService.getOne();
        return ResponseEntity.status(apiResponse.isStatus()?200:409).body(apiResponse);
    }

    //EMAIL BO'YICHA FOYDALANUVCHI MA'LUMOTLARINI QAYTARADI
//    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @GetMapping()
    public HttpEntity<?> getByEmail(@RequestParam String email){
        ApiResponse apiResponse = userService.getByEmail(email);
        return ResponseEntity.status(apiResponse.isStatus()?200:409).body(apiResponse);
    }

    //EMAILNI TASDIQLANG
    @GetMapping("/verifyEmail")
    public HttpEntity<?> verifyEmail(@RequestParam String email, @RequestParam String code) {
        ApiResponse apiResponse = userService.verifyEmail(email, code);
        return ResponseEntity.status(apiResponse.isStatus() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}