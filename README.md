<div align="center">
  <img style="align-self: center" src="src/main/resources/org/example/todo_list/images/logo/icon_256px.png" alt="logo" width="128"/>
  <h1>JustDo</h1>
</div>

![Static Badge](https://img.shields.io/badge/-Open_In_Intellij_IDEA-blue?logo=intellijidea) ![GitHub contributors](https://img.shields.io/github/contributors/joshesherr/CSC311-ToDo_List-GroupProject)
<br></br>


<h2>General Description</h2>
<p>JustDo is a task management application designed to help users efficiently organize their schedules and priorities. The application supports a variety of task types, including:</p>
  <ul>
    <li>General tasks</li>
    <li>Tests (Quizzes & Exams)</li>
    <li>Homework</li>
    <li>Projects</li>
    <li>Jobs/Work Shifts</li>
    <li>Meetings</li>
  </ul>
<p>JustDo tracks task progress, sets goals, and assists with time management. It is designed with the user’s productivity and convenience in mind.</p>

<h2>Technologies</h2>
<p>The JustDo application leverages modern technologies to deliver a seamless user experience:</p>
<p>
  <ul>
    <li>Primary IDE: IntelliJ IDEA</li>
    <li>Backend: Java JDK 22</li>
    <li>Dependency Management: Maven</li>
    <li>Frontend: JavaFX (Controls, Graphics, FXML) and CSS for styling</li>
    <li>Database: Microsoft Azure SQL Server</li>
  </ul>
</p>

<h2>Features</h2>
<p>
  <ul>
    <li><h3>Task Management</h3>
      <ul>
        <li>Create, edit, and delete task lists and tasks.</li>
        <li>Organize tasks into lists for better categorization.</li>
        <li>Copy and paste task information.</li>
      </ul>
    </li>
    <li><h3>Sorting & Filtering</h3>
      <ul>
        <li>Sort tasks by priority, due date, or name to ensure efficient organization.</li>
        <li>Filter tasks by priority level, allowing users to focus on high-priority or specific tasks.</li>
      </ul>
    </li>
    <li><h3>Progress Tracking</h3>
      <ul>
        <li>Special task groups allow the creation of subtasks.</li>
        <li>Progress bar show completion status for task lists and their subtasks.</li>
      </ul>
    </li>
  </ul>
</p>

<h2>Our Development and Design Process</h2>

![Process](https://github.com/user-attachments/assets/85597fcf-24da-4c34-b609-df21c3327aca)


<h2>How it Works</h2>

<h3>Sign In/Up window</h3>

![SignInUp](https://github.com/user-attachments/assets/4a11ec29-e883-4896-b6e5-b1e32c5b4cbe)


<p>When launching JustDo, users can either login with existing credentials or register as a new user.</p>
<p>The sign-up form validates user inputs using regular expressions to ensure proper formatting of fields like email and password.</p>

<h3>Login</h3>

![login1](https://github.com/user-attachments/assets/73a83f31-1000-44ff-b3a5-4197958fed05)

<p>The login window verifies user credentials against the database stored in Microsoft Azure.</p>
<p>Successful authentication initializes the current user's session and navigates to the Home Page, displaying the user’s personalized task lists. </p>

<h3>Main Window & Home Page</h3>
<img width="1624" alt="HomePage" src="https://github.com/user-attachments/assets/4f98364a-d71a-4ca2-9a4a-e724e5217211">
<p>The Home Page is the central hub where users manage their tasks and lists. Key features include:</p>
<p>
  <ul>
    <li>Creating task lists with custom names.</li>
    <li>Adding tasks with the following details: name, date, time, priority, description, and tags.</li>
  </ul>
</p>

![ListsTasks1](https://github.com/user-attachments/assets/ae904026-e96c-4cf6-bcd7-2ef76f928475)

<p>
  <ul>
    <li>Copying and pasting task information which simplifies task duplication or enables sharing task details across lists.</li>
    <li>Filtering tasks by priority level, enabling focus on specific categories of tasks.</li>
    <li>Sorting tasks:
      <ul>
        <li>By priority, so the most urgent tasks are addressed first.</li>
        <li>By due date, to keep the timeline on track.</li>
        <li>By name, for quick alphabetical organization.</li>
      </ul>
    </li>
  </ul>
</p>

![FilteringSorting](https://github.com/user-attachments/assets/e4815286-b9ca-4382-b39f-9e19cf0699ef)
<p>
  <ul>
        <li>Storing all lists and tasks securely in the Microsoft Azure SQL Server database.</li>
  </ul>
</p>

![databaseINF1](https://github.com/user-attachments/assets/906468ad-bdcc-4723-87e8-bf90e0e60174)

![DatabaseINF2](https://github.com/user-attachments/assets/7b906844-5a4c-408e-966b-b5c5eaf7de68)


<h2>Getting Started</h2>
<p>To run the Just Do application, ensure you have the following:</p>
<ol>
  <li>Java JDK 22 installed.</li>
  <li>An IDE like IntelliJ IDEA with Maven set up.</li>
  <li>Access to a Microsoft Azure SQL Server instance.</li>
</ol>
<p>Clone the repository and run the application using your IDE.</p>

<h2>Contributing</h2>
<p>We welcome contributions!</p>
<ul>
  <li>Fork the repository and create a new branch for your feature or bug fix.</li>
  <li>Submit a pull request with a detailed description of your changes.</li>
</ul>

<h2>Contact</h2>
<p>For questions or suggestions, please contact one of the contributors.</p>
