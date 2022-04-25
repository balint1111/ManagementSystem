package com.example.managementsystem.endpoints.user;

import com.example.managementsystem.endpoints.BaseController;
import com.example.managementsystem.endpoints.user.service.ListService;
import com.example.managementsystem.endpoints.user.service.GetPageService;
import com.example.managementsystem.endpoints.user.service.LoginService;
import com.example.managementsystem.endpoints.user.service.SaveService;
import com.example.managementsystem.entities.User;
import com.example.managementsystem.enumeration.CommonStatus;
import com.example.managementsystem.enumeration.UserType;
import com.example.managementsystem.request.GenericPageRequest;
import com.example.managementsystem.request.GenericSingleRequest;
import com.example.managementsystem.response.GenericListResponse;
import com.example.managementsystem.response.GenericPageResponse;
import com.example.managementsystem.response.GenericSingleResponse;
import com.example.managementsystem.system.HttpStatusEvaluate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Service("userController")
@RequestMapping("/user")
public class UserController extends BaseController {
    private final LoginService loginService;
    private final GetPageService getPageService;
    private final ListService getListService;
    private final SaveService saveService;

    public UserController(LoginService loginService, GetPageService getPageService, ListService getListService, SaveService saveService) {
        this.loginService = loginService;
        this.getPageService = getPageService;
        this.getListService = getListService;
        this.saveService = saveService;
    }

    @PostMapping("/save")
    private ResponseEntity<GenericSingleResponse<User>> saveService(@RequestBody GenericSingleRequest<User> request) {
        Long start = System.currentTimeMillis();
        GenericSingleResponse<User> response = !hasAnyAuthorities(UserType.ADMIN) ?
                new GenericSingleResponse<>(CommonStatus.FORBIDDEN.toString(), null, null) : saveService.service(request, new GenericSingleResponse<>());
        endpointLogging(start);
        return new ResponseEntity<>(response, HttpStatusEvaluate.evaluate(response));
    }

    @PostMapping("/login")
    private ResponseEntity<GenericSingleResponse<User>> login(@RequestBody GenericSingleRequest<User> request) {
        Long start = System.currentTimeMillis();
        GenericSingleResponse<User> response = !hasAnyAuthorities() ? 
                new GenericSingleResponse<>(CommonStatus.FORBIDDEN.toString(), null, null) : loginService.service(request, new GenericSingleResponse<>());
        endpointLogging(start);
        return new ResponseEntity<>(response, HttpStatusEvaluate.evaluate(response));
    }

    @GetMapping("/pageable")
    public ResponseEntity<GenericPageResponse<User>> pageService(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                                                                 @RequestParam(required = false, defaultValue = "", name = "search") String search) {
        Long start = System.currentTimeMillis();
        GenericPageResponse<User> response = !hasAnyAuthorities() ? 
                new GenericPageResponse<>(CommonStatus.FORBIDDEN.toString(), null, null) : getPageService.service(new GenericPageRequest<>(pageable, search), new GenericPageResponse<>());
        endpointLogging(start);
        return new ResponseEntity<>(response, HttpStatusEvaluate.evaluate(response));
    }

    @GetMapping("/list")
    public ResponseEntity<GenericListResponse<User>> listService(@RequestParam(required = false, defaultValue = "", name = "search") String search) {
        Long start = System.currentTimeMillis();
        GenericListResponse<User> response = !hasAnyAuthorities() ? 
                new GenericListResponse<>(CommonStatus.FORBIDDEN.toString(), null, null) : getListService.service(new GenericSingleRequest<>(search), new GenericListResponse<>());
        endpointLogging(start);
        return new ResponseEntity<>(response, HttpStatusEvaluate.evaluate(response));
    }
}
