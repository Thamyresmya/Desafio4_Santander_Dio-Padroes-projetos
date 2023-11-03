package dio.service;

import dio.model.Student;

/**
 * Interface que define o padrão <b>Strategy</b> no domínio de student. Com
 * isso, se necessário, podemos ter multiplas implementações dessa mesma
 * interface.
 */
public interface StudentService {

    Iterable<Student> buscarTodos();

    Student buscarPorId(Long id);

    void inserir(Student cliente);

    void atualizar(Long id, Student cliente);

    void deletar(Long id);

}