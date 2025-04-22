fun main() {
    val library = Library()
    val librarian = Librarian("John Doe", "L123")
    val member = Member("Alice", "M001")

    // Реєстрація члена
    librarian.register_new_member(member, library)

    val book1 = Book("Kotlin Programming", "JetBrains", "123-456-789")
    val book2 = Book("Advanced Kotlin", "JetBrains", "987-654-321")

    // Додавання книг до бібліотеки
    librarian.add_book_to_library(book1, library)
    librarian.add_book_to_library(book2, library)

    // Позичання книги
    library.borrow_book(member, book1)

    // Повернення книги
    library.return_book(member, book1)

    // Видалення книги з бібліотеки
    librarian.remove_book_from_library(book2, library)
}