
# Budget Tracker Application

Budget Tracker is a web-based application designed to help users track their expenses and manage their budget efficiently. The application allows users to add transactions, users, vendors, and categories to better manage their financial data. Built using Java, Thymeleaf, Spring Boot, HTML/CSS, and MySQL, Budget Tracker provides a user-friendly interface for easy financial tracking and reporting.

## Features

- **Transactions Management**: Add, edit, and delete transactions to track expenses.
- **Category and Vendor Management**: Add and manage categories and vendors to organize transactions.
- **User Management**: Add users to the system to track individual expenses.
- **Monthly Reports**: Generate reports summarizing expenses by month or category.
- **MVC Pattern**: Follows the Model-View-Controller (MVC) architecture for better structure.
- **Responsive Design**: User interface works across multiple devices.

## Development Process

The development of Budget Tracker followed a systematic approach:

- We used **Trello** to manage tasks via a Kanban board.
- We focused on high-priority features first, ensuring the core functionality of adding transactions was implemented before adding advanced features.
- **Entity Relationship Diagrams** (ERD) were used to model the database and define relationships between users, categories, vendors, and transactions.

# How to Run the Application

Make sure you have the following installed:

- **Java 11 or higher**
- **Maven**
- **MySQL** (or another relational database)
- **IDE** (e.g., IntelliJ IDEA, Eclipse)

### Steps:

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/budget-tracker.git
2. **Navigate to the project directory**:
   ```bash
   cd budget-tracker
3. **Build the project**:
    ```bash
   mvn clean install
4. **Run the application**:
   ```bash
   mvn spring-boot:run

## Future Enhancements

- **Budget Notifications**: Add notifications to alert users when nearing or exceeding their budget.
- **Multi-Currency Support**: Provide the ability to track expenses in multiple currencies with conversions.

## License

[MIT](https://choosealicense.com/licenses/mit/)
