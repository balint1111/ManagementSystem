package com.example.managementsystem.endpoints.education;

import com.example.managementsystem.endpoints.BaseController;
import com.example.managementsystem.endpoints.education.service.*;
import com.example.managementsystem.entities.Education;
import com.example.managementsystem.entities.User;
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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service("educationController")
@RequestMapping("/education")
public class EducationController extends BaseController {
    private final GetByIdService getByIdService;
    private final SaveService saveService;
    private final GetPageService getPageService;
    private final ListService listService;
    private final ListByToolCategoryService listByToolCategoryService;

    public EducationController(GetByIdService getByIdService, SaveService saveService, GetPageService getPageService, ListService listService, ListByToolCategoryService listByToolCategoryService) {
        this.getByIdService = getByIdService;
        this.saveService = saveService;
        this.getPageService = getPageService;
        this.listService = listService;
        this.listByToolCategoryService = listByToolCategoryService;
    }

    @PostMapping("/save")
    private ResponseEntity<GenericSingleResponse<User>> saveService(@RequestBody GenericSingleRequest<User> request){
        GenericSingleResponse<User> response = saveService.service(request, new GenericSingleResponse<>());
        return new ResponseEntity<>(response, HttpStatusEvaluate.evaluate(response));
    }

    @GetMapping("/get-by-id")
    private ResponseEntity<GenericSingleResponse<Education>> getByIdService(@RequestParam(name = "id") Long id){
        GenericSingleResponse<Education> response = getByIdService.service(new GenericSingleRequest<Long>(id), new GenericSingleResponse<>());
        return new ResponseEntity<>(response, HttpStatusEvaluate.evaluate(response));
    }

    @GetMapping("/list-by-tool-category")
    private ResponseEntity<GenericListResponse<Education>> listByToolCategory(@RequestParam(name = "id") Long id){
        GenericListResponse<Education> response = listByToolCategoryService.service(new GenericSingleRequest<Long>(id), new GenericListResponse<>());
        return new ResponseEntity<>(response, HttpStatusEvaluate.evaluate(response));
    }

    @GetMapping("/pageable")
    public ResponseEntity<GenericPageResponse<Education>> pageService(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                                                                        @RequestParam(required = false, defaultValue = "", name = "search") String search) {
        Long start = System.currentTimeMillis();
        GenericPageResponse<Education> response = getPageService.service(new GenericPageRequest<>(pageable, search), new GenericPageResponse<>());
        endpointLogging(start);
        return new ResponseEntity<>(response, HttpStatusEvaluate.evaluate(response));
    }

    @GetMapping("/list")
    public ResponseEntity<GenericListResponse<Education>> listService(@RequestParam(required = false, defaultValue = "", name = "search") String search) {
        Long start = System.currentTimeMillis();
        GenericListResponse<Education> response = listService.service(new GenericSingleRequest<>(search), new GenericListResponse<>());
        endpointLogging(start);
        return new ResponseEntity<>(response, HttpStatusEvaluate.evaluate(response));
    }
}
