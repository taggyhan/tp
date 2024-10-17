package hallpointer.address.logic.commands;

import static hallpointer.address.testutil.Assert.assertThrows;
import static hallpointer.address.testutil.TypicalIndexes.INDEX_FIRST_MEMBER;
import static hallpointer.address.testutil.TypicalSessions.ATTENDANCE;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import hallpointer.address.commons.core.GuiSettings;
import hallpointer.address.commons.core.index.Index;
import hallpointer.address.model.AddressBook;
import hallpointer.address.model.Model;
import hallpointer.address.model.ReadOnlyAddressBook;
import hallpointer.address.model.ReadOnlyUserPrefs;
import hallpointer.address.model.member.Member;
import hallpointer.address.model.member.UniqueMemberList;
import hallpointer.address.model.session.Session;
import hallpointer.address.model.session.SessionName;
import hallpointer.address.testutil.MemberBuilder;
import hallpointer.address.testutil.SessionBuilder;
import javafx.collections.ObservableList;

class AddSessionCommandTest {

    @Test
    public void constructor_nullSession_throwsNullPointerException() {
        Session session = new SessionBuilder(ATTENDANCE).build();
        Set<Index> indices = new HashSet<Index>();
        indices.add(INDEX_FIRST_MEMBER);

        assertThrows(NullPointerException.class, () -> new AddSessionCommand(null, indices));
        assertThrows(NullPointerException.class, () -> new AddSessionCommand(session, null));
        assertThrows(NullPointerException.class, () -> new AddSessionCommand(null, null));
    }

    @Test
    public void execute_sessionAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingSessionAdded modelStub = new ModelStubAcceptingSessionAdded();
        Session validSession = new SessionBuilder().build();
        Set<Index> indices = new HashSet<Index>();
        Set<Session> resultSessions = new HashSet<>();

        indices.add(INDEX_FIRST_MEMBER);
        modelStub.addMember(new MemberBuilder().build());
        CommandResult commandResult = new AddSessionCommand(validSession, indices).execute(modelStub);

        assertEquals(
                String.format(
                        AddSessionCommand.MESSAGE_SUCCESS,
                        validSession.getSessionName().sessionName,
                        validSession.getDate().fullDate,
                        validSession.getPoints().points,
                        indices.size()
                ),
                commandResult.getFeedbackToUser()
        );
        resultSessions.add(validSession);
        assertEquals(Collections.unmodifiableSet(resultSessions),
                modelStub.getFilteredMemberList().get(0).getSessions());
    }

    /*@Test
    public void execute_duplicateMember_throwsCommandException() {
        Member validMember = new MemberBuilder().build();
        AddMemberCommand addMemberCommand = new AddMemberCommand(validMember);
        AddMemberCommandTest.ModelStub modelStub = new AddMemberCommandTest.ModelStubWithMember(validMember);

        assertThrows(CommandException.class,
                AddMemberCommand.MESSAGE_DUPLICATE_MEMBER, () -> addMemberCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Member alice = new MemberBuilder().withName("Alice").build();
        Member bob = new MemberBuilder().withName("Bob").build();
        AddMemberCommand addAliceCommand = new AddMemberCommand(alice);
        AddMemberCommand addBobCommand = new AddMemberCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddMemberCommand addAliceCommandCopy = new AddMemberCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different member -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddMemberCommand addMemberCommand = new AddMemberCommand(ALICE);
        String expected = AddMemberCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addMemberCommand.toString());
    } */

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMember(Member member) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasMember(Member member) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteMember(Member target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMember(Member target, Member editedMember) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addSession(Session session) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public ObservableList<Member> getFilteredMemberList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredMemberList(Predicate<Member> predicate) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public boolean hasSession(Session session) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteSession(Session sessionToDelete) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public void deleteSession(Member target, SessionName sessionName) {
            throw new AssertionError("This method should not be called.");

        }

        @Override
        public void setSession(Session target, Session editedSession) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Session> getSessionList() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that always accept the session being added.
     */
    private class ModelStubAcceptingSessionAdded extends ModelStub {
        final UniqueMemberList members = new UniqueMemberList();
        final ArrayList<Session> sessionsAdded = new ArrayList<>();

        @Override
        public void addMember(Member member) {
            members.add(member);
        }

        @Override
        public ObservableList<Member> getFilteredMemberList() {
            return members.asUnmodifiableObservableList();
        }

        @Override
        public void updateFilteredMemberList(Predicate<Member> predicate) {}

        @Override
        public boolean hasSession(Session session) {
            requireNonNull(session);
            return sessionsAdded.stream().anyMatch(session::isSameSession);
        }

        @Override
        public void addSession(Session session) {
            requireNonNull(session);
            sessionsAdded.add(session);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
