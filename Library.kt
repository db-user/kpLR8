data class Book(
    val title: String,
    val author: String,
    val isbn: String,
    var available: Boolean = true,
    var borrower: Member? = null
) {
    fun isAvailable(): Boolean = available
    fun borrow(member: Member) {
        if (available) {
            available = false
            borrower = member
            member.borrow_book(this)
        } else {
            println("Book is not available.")
        }
    }
    fun returnBook() {
        available = true
        borrower?.return_book(this)
        borrower = null
    }
}

class Member(
    val name: String,
    val memberId: String,
    val maxBooks: Int = 3
) {
    val borrowedBooks: MutableList<Book> = mutableListOf()

    fun borrow_book(book: Book) {
        if (can_borrow()) {
            borrowedBooks.add(book)
            // Логування дії позичання
            runBlocking {
                sendLog(
                    title = "Book Borrowed",
                    body = "Member $name borrowed '${book.title}'."
                )
            }
        } else {
            println("$name can't borrow more books.")
            // Логування неуспішного позичання
            runBlocking {
                sendLog(
                    title = "Borrow Failed",
                    body = "Member $name tried to borrow '${book.title}' but exceeded the limit."
                )
            }
        }
    }

    fun return_book(book: Book) {
        if (borrowedBooks.contains(book)) {
            borrowedBooks.remove(book)
            // Логування дії повернення книги
            runBlocking {
                sendLog(
                    title = "Book Returned",
                    body = "Member $name returned '${book.title}'."
                )
            }
        } else {
            println("$name didn't borrow this book.")
            // Логування неуспішного повернення
            runBlocking {
                sendLog(
                    title = "Return Failed",
                    body = "Member $name tried to return '${book.title}', which wasn't borrowed."
                )
            }
        }
    }

    fun can_borrow(): Boolean = borrowedBooks.size < maxBooks
}

class Library {
    val books: MutableList<Book> = mutableListOf()
    val members: MutableList<Member> = mutableListOf()

    fun add_book(book: Book) {
        books.add(book)
    }

    fun remove_book(book: Book) {
        books.remove(book)
    }

    fun find_book_by_title(title: String): Book? {
        return books.find { it.title == title }
    }

    fun register_member(member: Member) {
        members.add(member)
    }

    fun unregister_member(member: Member) {
        members.remove(member)
    }

    fun borrow_book(member: Member, book: Book) {
        if (book.isAvailable() && member.can_borrow()) {
            book.borrow(member)
        } else {
            println("Cannot borrow book.")
        }
    }

    fun return_book(member: Member, book: Book) {
        book.returnBook()
    }
}

class Librarian(
    val name: String,
    val employeeId: String
) {
    fun add_book_to_library(book: Book, library: Library) {
        library.add_book(book)
        // Логування додавання книги
        runBlocking {
            sendLog(
                title = "Book Added",
                body = "Librarian $name added '${book.title}' by ${book.author}."
            )
        }
    }

    fun remove_book_from_library(book: Book, library: Library) {
        library.remove_book(book)
        // Логування видалення книги
        runBlocking {
            sendLog(
                title = "Book Removed",
                body = "Librarian $name removed '${book.title}'."
            )
        }
    }

    fun register_new_member(member: Member, library: Library) {
        library.register_member(member)
        // Логування реєстрації нового члена
        runBlocking {
            sendLog(
                title = "New Member",
                body = "Librarian $name registered member '${member.name}' with ID ${member.memberId}."
            )
        }
    }
}