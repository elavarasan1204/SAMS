package com.sams.controller;

import com.sams.dto.ApiResponse;
import com.sams.entity.Hod;
import com.sams.entity.Department;
import com.sams.service.HodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hod")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class HodController {

    @Autowired
    private HodService hodService;

    private Long toLong(Object obj) {
        if (obj == null) return null;
        if (obj instanceof Number) return ((Number) obj).longValue();
        String str = obj.toString().trim();
        return str.isEmpty() ? null : Long.valueOf(str);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllHods() {
        return ResponseEntity.ok(ApiResponse.success("HODs retrieved", hodService.getAllHods()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getHodById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("HOD found", hodService.getHodById(id)));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getHodByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.success("HOD found", hodService.getHodByUserId(userId)));
    }

    @GetMapping("/department/{deptId}")
    public ResponseEntity<ApiResponse> getHodByDepartment(@PathVariable Long deptId) {
        return ResponseEntity.ok(ApiResponse.success("HOD found", hodService.getHodByDepartment(deptId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createHod(@RequestBody Map<String, Object> body) {
        Hod hod = new Hod();
        hod.setEmployeeId((String) body.get("employeeId"));
        hod.setFullName((String) body.get("fullName"));
        hod.setEmail((String) body.get("email"));
        hod.setPhone((String) body.get("phone"));
        hod.setQualification((String) body.get("qualification"));

        Long deptId = toLong(body.get("departmentId"));
        if (deptId != null) {
            hod.setDepartment(new Department());
            hod.getDepartment().setId(deptId);
        }

        String password = body.get("password") != null && !body.get("password").toString().isEmpty()
                ? body.get("password").toString() : "Hod@123";

        Hod created = hodService.createHod(hod, password);
        return ResponseEntity.ok(ApiResponse.success("HOD created successfully", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateHod(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Hod hod = new Hod();
        hod.setEmployeeId((String) body.get("employeeId"));
        hod.setFullName((String) body.get("fullName"));
        hod.setEmail((String) body.get("email"));
        hod.setPhone((String) body.get("phone"));
        hod.setQualification((String) body.get("qualification"));

        Long deptId = toLong(body.get("departmentId"));
        if (deptId != null) {
            hod.setDepartment(new Department());
            hod.getDepartment().setId(deptId);
        }

        Hod updated = hodService.updateHod(id, hod);
        return ResponseEntity.ok(ApiResponse.success("HOD updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteHod(@PathVariable Long id) {
        hodService.deleteHod(id);
        return ResponseEntity.ok(ApiResponse.success("HOD deactivated successfully"));
    }
}
