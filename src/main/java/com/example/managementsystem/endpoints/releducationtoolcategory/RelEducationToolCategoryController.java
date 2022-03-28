package com.example.managementsystem.endpoints.releducationtoolcategory;


import com.example.managementsystem.endpoints.BaseController;
import com.example.managementsystem.endpoints.releducationtoolcategory.service.GetByIdService;
import com.example.managementsystem.endpoints.releducationtoolcategory.service.GetPageService;
import com.example.managementsystem.endpoints.releducationtoolcategory.service.ListService;
import com.example.managementsystem.endpoints.releducationtoolcategory.service.SaveService;
import com.example.managementsystem.entities.Education;
import com.example.managementsystem.entities.RelEducationToolCategory;
import com.example.managementsystem.entities.User;
import com.example.managementsystem.request.GenericPageRequest;
import com.example.managementsystem.request.GenericSingleRequest;
import com.example.managementsystem.response.GenericListResponse;
import com.example.managementsystem.response.GenericPageResponse;
import com.example.managementsystem.response.GenericSingleResponse;
import com.example.managementsystem.system.HttpStatusEvaluate;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Service("relEducationToolCategoryController")
@RequestMapping("/rel-education-tool-category")
public class RelEducationToolCategoryController extends BaseController {
    private final GetByIdService getByIdService;
    private final SaveService saveService;
    private final GetPageService getPageService;
    private final ListService listService;

    public RelEducationToolCategoryController(GetByIdService getByIdService, SaveService saveService, GetPageService getPageService, ListService listService) {
        this.getByIdService = getByIdService;
        this.saveService = saveService;
        this.getPageService = getPageService;
        this.listService = listService;
    }

    @PostMapping("/save")
    private ResponseEntity<GenericSingleResponse<RelEducationToolCategory>> saveService(@RequestBody GenericSingleRequest<RelEducationToolCategory> request){
        GenericSingleResponse<RelEducationToolCategory> response = saveService.service(request, new GenericSingleResponse<>());
        return new ResponseEntity<>(response, HttpStatusEvaluate.evaluate(response));
    }

    @GetMapping("/get-by-id")
    private ResponseEntity<GenericSingleResponse<RelEducationToolCategory>> getByIdService(@RequestParam(name = "id") Long id){
        Long start = System.currentTimeMillis();
        GenericSingleResponse<RelEducationToolCategory> response = getByIdService.service(new GenericSingleRequest<Long>(id), new GenericSingleResponse<>());
        endpointLogging(start);
        return new ResponseEntity<>(response, HttpStatusEvaluate.evaluate(response));
    }

    @GetMapping("/pageable")
    public ResponseEntity<GenericPageResponse<RelEducationToolCategory>> pageService(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                                                                      @RequestParam(required = false, defaultValue = "", name = "search") String search) {
        Long start = System.currentTimeMillis();
        GenericPageResponse<RelEducationToolCategory> response = getPageService.service(new GenericPageRequest<>(pageable, search), new GenericPageResponse<>());
        endpointLogging(start);
        return new ResponseEntity<>(response, HttpStatusEvaluate.evaluate(response));
    }

    @GetMapping("/list")
    public ResponseEntity<GenericListResponse<RelEducationToolCategory>> listService(@RequestParam(required = false, defaultValue = "", name = "search") String search) {
        Long start = System.currentTimeMillis();
        GenericListResponse<RelEducationToolCategory> response = listService.service(new GenericSingleRequest<>(search), new GenericListResponse<>());
        endpointLogging(start);
        return new ResponseEntity<>(response, HttpStatusEvaluate.evaluate(response));
    }
}
