import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.HashMap;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TodoTest.class)
public class TodoTest {
    final String SAMPLE_TASK_TITLE_ONE = "Arrumar a casa";
    final String SAMPLE_TASK_TITLE_TWO = "Marcar Consulta";
    final String GET_TASKS_METHOD = "getTasks";
    @Mock
    Todo todo;

    @Before
    public void setUp() {
        todo = new Todo();
    }

    @Test
    public void testAddTaskToEmptyTodo() {
        HashMap<String, Boolean> tasks = todo.addTask(SAMPLE_TASK_TITLE_ONE);
        Assert.assertEquals(tasks.size(), 1);
        Assert.assertEquals(tasks.get(SAMPLE_TASK_TITLE_ONE), false);
    }

    @Test
    public void testAddTaskToTodoWithOneOtherCompleteTask() throws Exception {
        Todo spy = PowerMockito.spy(todo);
        HashMap<String, Boolean> todoWithOneTask = new HashMap<String, Boolean>() {{
            put(SAMPLE_TASK_TITLE_TWO, false);
        }};
        PowerMockito.when(spy, GET_TASKS_METHOD).thenReturn(todoWithOneTask);

        HashMap<String, Boolean> tasks = spy.addTask(SAMPLE_TASK_TITLE_ONE);
        Assert.assertEquals(tasks.size(), 2);
        Assert.assertFalse(tasks.get(SAMPLE_TASK_TITLE_ONE));
        Assert.assertFalse(tasks.get(SAMPLE_TASK_TITLE_TWO));
        PowerMockito.verifyPrivate(spy, Mockito.times(2)).invoke(GET_TASKS_METHOD);
    }

    @Test
    public void testAddTaskToTodoWithOneOtherIncompleteTask() throws Exception {
        Todo spy = PowerMockito.spy(todo);
        HashMap<String, Boolean> todoWithOneTask = new HashMap<String, Boolean>() {{
            put(SAMPLE_TASK_TITLE_TWO, true);
        }};
        PowerMockito.when(spy, GET_TASKS_METHOD).thenReturn(todoWithOneTask);

        HashMap<String, Boolean> tasks = spy.addTask(SAMPLE_TASK_TITLE_ONE);
        Assert.assertEquals(tasks.size(), 2);
        Assert.assertFalse(tasks.get(SAMPLE_TASK_TITLE_ONE));
        Assert.assertTrue(tasks.get(SAMPLE_TASK_TITLE_TWO));
        PowerMockito.verifyPrivate(spy, Mockito.times(2)).invoke(GET_TASKS_METHOD);
    }

    @Test
    public void testAddTaskToTodoWithSameTask() throws Exception {
        Todo spy = PowerMockito.spy(todo);
        HashMap<String, Boolean> todoWithOneTask = new HashMap<String, Boolean>() {{
            put(SAMPLE_TASK_TITLE_ONE, false);
        }};
        PowerMockito.when(spy, GET_TASKS_METHOD).thenReturn(todoWithOneTask);

        HashMap<String, Boolean> tasks = spy.addTask(SAMPLE_TASK_TITLE_ONE);
        Assert.assertEquals(tasks.size(), 1);
        Assert.assertFalse(tasks.get(SAMPLE_TASK_TITLE_ONE));
        PowerMockito.verifyPrivate(spy, Mockito.times(2)).invoke(GET_TASKS_METHOD);
    }

    @Test
    public void testListCompleteTasksInEmptyTodo() {
        ArrayList<String> tasks = todo.incompleteTasks();
        Assert.assertEquals(tasks.size(), 0);
    }

    @Test
    public void testListCompleteTasksInTodoWithOneCompleteTask() throws Exception {
        Todo spy = PowerMockito.spy(todo);
        HashMap<String, Boolean> todoWithOneTask = new HashMap<String, Boolean>() {{
            put(SAMPLE_TASK_TITLE_ONE, false);
        }};
        PowerMockito.when(spy, GET_TASKS_METHOD).thenReturn(todoWithOneTask);

        ArrayList<String> tasks = spy.incompleteTasks();
        Assert.assertEquals(tasks.size(), 1);
        Assert.assertEquals(tasks.get(0), SAMPLE_TASK_TITLE_ONE);
        PowerMockito.verifyPrivate(spy, Mockito.times(1)).invoke(GET_TASKS_METHOD);
    }

    @Test
    public void testListCompleteTasksInTodoWithOneIncompleteTask() throws Exception {
        Todo spy = PowerMockito.spy(todo);
        HashMap<String, Boolean> todoWithOneTask = new HashMap<String, Boolean>() {{
            put(SAMPLE_TASK_TITLE_ONE, true);
        }};
        PowerMockito.when(spy, GET_TASKS_METHOD).thenReturn(todoWithOneTask);

        ArrayList<String> tasks = spy.incompleteTasks();
        Assert.assertEquals(tasks.size(), 0);
        PowerMockito.verifyPrivate(spy, Mockito.times(1)).invoke(GET_TASKS_METHOD);
    }

    @Test
    public void testCompleteAnIncompleteTask() {
        todo.addTask(SAMPLE_TASK_TITLE_ONE);
        todo.completeTask(SAMPLE_TASK_TITLE_ONE);
        Assert.assertTrue(todo.getTasks().get(SAMPLE_TASK_TITLE_ONE));
    }

    @Test
    public void testCompleteAnCompletedTask() throws Exception {
        Todo spy = PowerMockito.spy(todo);
        HashMap<String, Boolean> todoWithOneTask = new HashMap<String, Boolean>() {{
            put(SAMPLE_TASK_TITLE_ONE, true);
        }};
        PowerMockito.when(spy, GET_TASKS_METHOD).thenReturn(todoWithOneTask);

        Assert.assertTrue(spy.getTasks().get(SAMPLE_TASK_TITLE_ONE));
        PowerMockito.verifyPrivate(spy, Mockito.times(1)).invoke(GET_TASKS_METHOD);
    }

    @Test
    public void testIncompleteAnIncompleteTask() {
        todo.addTask(SAMPLE_TASK_TITLE_ONE);
        todo.incompleteTask(SAMPLE_TASK_TITLE_ONE);
        Assert.assertFalse(todo.getTasks().get(SAMPLE_TASK_TITLE_ONE));
    }

    @Test
    public void testIncompleteAnCompletedTask() throws Exception {
        Todo spy = PowerMockito.spy(todo);
        HashMap<String, Boolean> todoWithOneTask = new HashMap<String, Boolean>() {{
            put(SAMPLE_TASK_TITLE_ONE, true);
        }};
        PowerMockito.when(spy, GET_TASKS_METHOD).thenReturn(todoWithOneTask);

        spy.incompleteTask(SAMPLE_TASK_TITLE_ONE);

        PowerMockito.verifyPrivate(spy, Mockito.times(1)).invoke(GET_TASKS_METHOD);
        Assert.assertFalse(spy.getTasks().get(SAMPLE_TASK_TITLE_ONE));
    }

    @Test
    public void testRemoveTaskFromEmptyTodo() {
        todo.removeTask(SAMPLE_TASK_TITLE_ONE);
        Assert.assertEquals(todo.getTasks().size(), 0);
    }

    @Test
    public void testRemoveTaskFromTodoWithOneTaskIfExists() {
        todo.addTask(SAMPLE_TASK_TITLE_ONE);
        todo.removeTask(SAMPLE_TASK_TITLE_ONE);
        Assert.assertEquals(todo.getTasks().size(), 0);
    }

    @Test
    public void testRemoveTaskFromTodoWithOneTaskIfNotExists() {
        todo.addTask(SAMPLE_TASK_TITLE_ONE);
        todo.removeTask(SAMPLE_TASK_TITLE_TWO);
        Assert.assertEquals(todo.getTasks().size(), 1);
        Assert.assertFalse(todo.getTasks().get(SAMPLE_TASK_TITLE_ONE));
    }
}