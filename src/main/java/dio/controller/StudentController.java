package dio.controller;

import dio.model.Student;
import dio.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Esse {@link RestController} representa nossa <b>Facade</b>, pois abstrai toda
 * a complexidade de integrações (Banco de Dados e API do ViaCEP) em uma
 * interface simples e coesa (API REST).
 */
@RestController
@RequestMapping("student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<Iterable<Student>> buscarTodos() {
        return ResponseEntity.ok(studentService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Student> inserir(@RequestBody Student student) {
        studentService.inserir(student);
        return ResponseEntity.ok(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> atualizar(@PathVariable Long id, @RequestBody Student student) {
        studentService.atualizar(id, student);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        studentService.deletar(id);
        return ResponseEntity.ok().build();
    }
}