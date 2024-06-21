package com.riwi.libros.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.riwi.libros.dto.BookDTO;
import com.riwi.libros.entity.Book;
import com.riwi.libros.exception.ResourceNotFoundException;
import com.riwi.libros.mapper.BookMapper;
import com.riwi.libros.repository.BookRepository;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookMapper bookMapper;

    public BookDTO createBook(BookDTO bookDTO) {
        Book book = bookMapper.toBook(bookDTO);
        book = bookRepository.save(book);
        return bookMapper.toBookDTO(book);
    }

    public BookDTO getBook(Long bookId) {
        return bookRepository.findById(bookId)
                .map(bookMapper::toBookDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

    public BookDTO updateBook(Long bookId, BookDTO bookDTO) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPublicationYear(bookDTO.getPublicationYear());
        book.setGenre(bookDTO.getGenre());
        book.setIsbn(bookDTO.getIsbn());
        book = bookRepository.save(book);
        return bookMapper.toBookDTO(book);
    }

    public void deleteBook(Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new ResourceNotFoundException("Book not found");
        }
        bookRepository.deleteById(bookId);
    }
}
