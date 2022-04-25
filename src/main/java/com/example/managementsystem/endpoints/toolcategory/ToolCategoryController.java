package com.example.managementsystem.endpoints.toolcategory;


import com.example.managementsystem.endpoints.BaseController;
import com.example.managementsystem.endpoints.toolcategory.service.GetByIdService;
import com.example.managementsystem.endpoints.toolcategory.service.GetPageService;
import com.example.managementsystem.endpoints.toolcategory.service.ListService;
import com.example.managementsystem.endpoints.toolcategory.service.SaveService;
import com.example.managementsystem.entities.ToolCategory;
import com.example.managementsystem.enumeration.CommonStatus;
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

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Service("toolCategoryController")
@RequestMapping("/tool-category")
public class ToolCategoryController extends BaseController {
    private final GetByIdService getByIdService;
    private final SaveService saveService;
    private final GetPageService getPageService;
    private final ListService listService;

    public ToolCategoryController(GetByIdService getByIdService, SaveService saveService, GetPageService getPageService, ListService listService) {
        this.getByIdService = getByIdService;
        this.saveService = saveService;
        this.getPageService = getPageService;
        this.listService = listService;
    }

    @PostMapping("/save")
    private ResponseEntity<GenericSingleResponse<ToolCategory>> saveService(@RequestBody GenericSingleRequest<ToolCategory> request){
        Long start = System.currentTimeMillis();
        GenericSingleResponse<ToolCategory> response = !hasAnyAuthorities() ? 
                new GenericSingleResponse<>(CommonStatus.FORBIDDEN.toString(), null, null) : saveService.service(request, new GenericSingleResponse<>());
        endpointLogging(start);
        return new ResponseEntity<>(response, HttpStatusEvaluate.evaluate(response));
    }

    @GetMapping("/get-by-id")
    private ResponseEntity<GenericSingleResponse<ToolCategory>> getByIdService(@RequestParam(name = "id") Long id){
        Long start = System.currentTimeMillis();
        GenericSingleResponse<ToolCategory> response = !hasAnyAuthorities() ? 
                new GenericSingleResponse<>(CommonStatus.FORBIDDEN.toString(), null, null) : getByIdService.service(new GenericSingleRequest<Long>(id), new GenericSingleResponse<>());
        endpointLogging(start);
        return new ResponseEntity<>(response, HttpStatusEvaluate.evaluate(response));
    }

    @GetMapping("/pageable")
    public ResponseEntity<GenericPageResponse<ToolCategory>> pageService(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                                                                      @RequestParam(required = false, defaultValue = "", name = "search") String search) {
        Long start = System.currentTimeMillis();
        GenericPageResponse<ToolCategory> response = !hasAnyAuthorities() ? 
                new GenericPageResponse<>(CommonStatus.FORBIDDEN.toString(), null, null) : getPageService.service(new GenericPageRequest<>(pageable, search), new GenericPageResponse<>());
        endpointLogging(start);
        return new ResponseEntity<>(response, HttpStatusEvaluate.evaluate(response));
    }

    @GetMapping("/list")
    public ResponseEntity<GenericListResponse<ToolCategory>> listService(@RequestParam(required = false, defaultValue = "", name = "search") String search) {
        Long start = System.currentTimeMillis();
        GenericListResponse<ToolCategory> response = !hasAnyAuthorities() ? 
                new GenericListResponse<>(CommonStatus.FORBIDDEN.toString(), null, null) : listService.service(new GenericSingleRequest<>(search), new GenericListResponse<>());
        endpointLogging(start);
        return new ResponseEntity<>(response, HttpStatusEvaluate.evaluate(response));
    }
}
