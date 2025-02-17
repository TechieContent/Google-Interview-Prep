import java.util.*;

class Employee {
    public int id;
    public int importance;
    public List<Integer> subordinates;

    public Employee(int id, int importance, List<Integer> subordinates) {
        this.id = id;
        this.importance = importance;
        this.subordinates = subordinates;
    }
}

public class Main {

    public static int getImportance(List<Employee> employees, int id) {
        // Step 1: Create a map for quick lookup
        Map<Integer, Employee> employeeMap = new HashMap<>();
        for (Employee emp : employees) {
            employeeMap.put(emp.id, emp);
        }

        // Step 2: Call the DFS function
        return dfs(id, employeeMap);
    }

    private static int dfs(int id, Map<Integer, Employee> employeeMap) {
        Employee employee = employeeMap.get(id);
        int importance = employee.importance;

        // Traverse subordinates
        for (int subId : employee.subordinates) {
            importance += dfs(subId, employeeMap);
        }

        return importance;
    }

    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
            new Employee(1, 5, Arrays.asList(2, 3)),
            new Employee(2, 3, Arrays.asList()),
            new Employee(3, 3, Arrays.asList())
        );

        int result = getImportance(employees, 1);
        System.out.println("Total Importance: " + result);
    }
}
