package dio.service.Impl;

import dio.model.Student;
import dio.model.StudentRepository;
import dio.model.Endereco;
import dio.model.EnderecoRepository;
import dio.service.StudentService;
import dio.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementação da <b>Strategy</b> {@link StudentService}, a qual pode ser
 * injetada pelo Spring (via {@link Autowired}). Com isso, como essa classe é um
 * {@link Service}, ela será tratada como um <b>Singleton</b>.
 */
@Service
public class StudentServiceImpl implements StudentService {

    // Singleton: Injetar os componentes do Spring com @Autowired.
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ViaCepService viaCepService;

    // Strategy: Implementar os métodos definidos na interface.
    // Facade: Abstrair integrações com subsistemas, provendo uma interface simples.

    @Override
    public Iterable<Student> buscarTodos() {
        // Buscar todos os Students.
        return studentRepository.findAll();
    }

    @Override
    public Student buscarPorId(Long id) {
        // Buscar Student por ID.
        Optional<Student> student = studentRepository.findById(id);
        return student.get();
    }

    @Override
    public void inserir(Student student) {
        salvarStudentComCep(student);
    }

    @Override
    public void atualizar(Long id, Student student) {
        // Buscar student por ID, caso exista:
        Optional<Student> studentBd = studentRepository.findById(id);
        if (studentBd.isPresent()) {
            salvarStudentComCep(student);
        }
    }

    @Override
    public void deletar(Long id) {
        // Deletar student por ID.
        studentRepository.deleteById(id);
    }

    //metodo privado
    private void salvarStudentComCep(Student student) {
        // Verificar se o Endereco do student já existe (pelo CEP).
        String cep = student.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            // Caso não exista, integrar com o ViaCEP e persistir o retorno.
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        student.setEndereco(endereco);
        // Inserir student, vinculando o Endereco (novo ou existente).
        studentRepository.save(student);
    }

}
