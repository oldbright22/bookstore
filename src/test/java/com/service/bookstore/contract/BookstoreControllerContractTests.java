package com.service.bookstore.contract;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import au.com.dius.pact.provider.spring.target.MockMvcTarget;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import com.service.bookstore.controller.BookController;
import com.service.bookstore.model.Book;
import com.service.bookstore.model.BookStatus;
import com.service.bookstore.model.BookRatings;
import com.service.bookstore.repository.BookRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

//@SpringBootTest
@RunWith(SpringRestPactRunner.class)
@Provider("driverManagementProvider")
@PactBroker(host = "localhost", port = "9292")
public class BookstoreControllerContractTests {

	// Mock objects are used to simulate the behavior of a real object and it does not do observability,
	// while a spy object is used to observe and record the behavior of a real object.


	// @Mock creates a mock.
	// @InjectMocks creates an instance of the class and injects the mocks that are created with the @Mock (or @Spy) annotations into this instance.
    // Note you must use @RunWith(MockitoJUnitRunner.class) or Mockito.initMocks(this)
	// to initialize these mocks and inject them (JUnit 4).
    // With JUnit 5, you must use @ExtendWith(MockitoExtension.class).

	// Difference between @Mock and @Spy
	// When using @Mock, mockito creates a bare-bones shell instance of the field type,
	// entirely instrumented to track interactions with it.
	// -------->>>>> This is NOT a real object and does NOT maintain the state changes to it.

	// When using @Spy, mockito creates YES a real instance of the class and
	// YES it tracks every interaction with it.
	// YES it maintains the state changes to it.

	@InjectMocks
	private BookController controller = new BookController();
	@Mock
	private BookRepository repository;
	@TestTarget
	public final MockMvcTarget target = new MockMvcTarget();

	//@Test
	//void contextLoads() {
	//}

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		target.setControllers(controller);
	}

	@State("get-books-green-rating")
	public void testGetBooksWithRatingGreen() {
		target.setRunTimes(1);
		Mockito.when(repository.booksByRating(BookRatings.GREEN))
				.thenReturn(Collections.singletonList(new Book(UUID.randomUUID(), "3The Hunger Games", "Suzanne Collins", 39.99, 5.00, LocalDate.now().minusYears(5), BookStatus.AVAILABLE, BookRatings.GREEN )));
	}

	@State("update-book")
	public void testUpdateBook() {
		target.setRunTimes(1);
		UUID id = UUID.randomUUID();
		Book book = new Book(id, "4The Hunger Games", "Suzanne Collins", 39.99, 5.00, LocalDate.now().minusYears(5), BookStatus.AVAILABLE, BookRatings.GREEN );
		Mockito.when(repository.findById(id)).thenReturn(Optional.of(book));
		Mockito.when(repository.updateBook(id,book))
				.thenReturn(new Book(id, "5The Hunger Games", "Suzanne Collins", 49.99, 8.00, LocalDate.now().minusYears(8), BookStatus.AVAILABLE, BookRatings.YELLOW ));
	}


}


/*  EXAMPLE

VERY USEFULT TO READ
https://dzone.com/articles/a-guide-to-mocking-with-mockito
https://javacodehouse.com/blog/mockito-tutorial/


https://github.com/querydsl/querydsl/blob/master/querydsl-examples/querydsl-example-sql-spring/src/main/java/com/querydsl/example/dao/CustomerDao.java
https://stackoverflow.com/questions/7232080/how-to-design-a-dao-class

//understand better  classDAO,  classDAOImpl
https://github.com/chaudharyjr/spring-mvc-crud/blob/master/src/com/chaudharyjr/springmvc/DAO/CustomerDAOImpl.java
https://stackoverflow.com/questions/53739152/error-creating-bean-with-name-customercontroller-unsatisfied-dependency-expre

https://dzone.com/articles/is-observability-replacing-testing?fromrel=true

@Mock creates a mock implementation for the classes you need.
@InjectMock creates an instance of the class and injects the mocks that are marked with the annotations @Mock into it.

The @Spy annotation is used to create a real object and spy on that real object.

A spy helps to call all the normal methods of the object while still tracking every interaction,
just as we would with a mock.


Mock objects are used to simulate the behavior of a real object and it does not do observability,
while a spy object is used to observe and record the behavior of a real object.


From Teacher example:
On the other hand, Spy used to monitor the behavior of an object under test.

Suppose you are working on a social media application, and you want to test the functionality of the user profile module. The user profile module has a method that updates the user's profile picture and sends a notification to the user's followers. You want to test this method and also verify that the notification is sent correctly. We can use spy in this context as it monitors the behaviour of the notification on update



@Mock
StudentDao studentDao;

@InjectMocks
StudentService service;

@Before
public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
}

https://stackoverflow.com/questions/16467685/difference-between-mock-and-injectmocks

class Game {

    private Player player;

    public Game(Player player) {
        this.player = player;
    }

    public String attack() {
        return "Player attack with: " + player.getWeapon();
    }

}

class Player {

    private String weapon;

    public Player(String weapon) {
        this.weapon = weapon;
    }

    String getWeapon() {
        return weapon;
    }
}


TEST CLASS
Mockito will mock a Player class and it's behaviour using when and thenReturn method.
Lastly, using @InjectMocks Mockito will put that Player into Game.

@RunWith(MockitoJUnitRunner.class)
class GameTest {

    @Mock
    Player player;

    @InjectMocks
    Game game;

    @Test
    public void attackWithSwordTest() throws Exception {
        Mockito.when(player.getWeapon()).thenReturn("Sword");

        assertEquals("Player attack with: Sword", game.attack());
    }

}



EXAMPLE #2

@RunWith(MockitoJUnitRunner.class)
public class GameTest {

  @Mock Player player;

  @Spy List<String> enemies = new ArrayList<>();

  @InjectMocks Game game;

  @Test public void attackWithSwordTest() throws Exception {
    Mockito.when(player.getWeapon()).thenReturn("Sword");

    enemies.add("Dragon");
    enemies.add("Orc");

    assertEquals(2, game.numberOfEnemies());

    assertEquals("Player attack with: Sword", game.attack());
  }
}


 We will also get same behaviour using @Spy annotation. Even if the attribute name is different

 class Game {

  private Player player;

  private List<String> opponents;

  public Game(Player player, List<String> opponents) {
    this.player = player;
    this.opponents = opponents;
  }

  public int numberOfEnemies() {
    return opponents.size();
  }



 */