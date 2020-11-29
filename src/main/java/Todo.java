import java.util.ArrayList;
import java.util.HashMap;

public class Todo {
    // Key: Title
    // Value: Completed?(true,false)
    private final HashMap<String, Boolean> tasks = new HashMap<String, Boolean>();

    public HashMap<String, Boolean> addTask(String title) {
        this.getTasks().put(title, false);
        return this.getTasks();
    }

    public void removeTask(String title) {
        this.getTasks().remove(title);
    }

    public void completeTask(String title) {
        this.getTasks().put(title, true);
    }

    public void incompleteTask(String title) {
        this.getTasks().put(title, false);
    }

    public ArrayList<String> incompleteTasks() {
        ArrayList<String> tasks = new ArrayList<String>();
        for (HashMap.Entry<String, Boolean> task : this.getTasks().entrySet()) {
            boolean isComplete = !task.getValue();
            if (isComplete) {
                System.out.println(task.getKey());
                tasks.add(task.getKey());
            }
        }
        return tasks;
    }

    public HashMap<String, Boolean> getTasks() {
        return this.tasks;
    }
}
