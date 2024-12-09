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
<p>The application will primarily allow users to create and manage tasks, with the additional ability to create special task groups which contain sub-tasks. The user will be able to complete subtasks in the special task groups, checking them off to increase the task’s progress bar towards completion. Users can name these special task groups whatever they desire, and assign tags to be able to filter tasks by tag in addition to filtering by date. Users will be able to assign tasks priority settings based on their importance. The app will also send reminders for upcoming deadlines if the user desires, which will be determined by the due dates of tasks or starting times of meetings. A user will receive these reminders if they elect to via e-mail or notification. 
</p>

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


<h2>GUI Views</h2>
<p>The application will utilize a variety of GUI views to display User task data according to the users needs at that time. The default view, or home page, will display all of a user’s individual tasks that they have created up until this point, along with tabs inserted into a FlowPane that allow a user to filter between personal tasks, group tasks, today tasks and recently completed tasks. Additionally, a user will be able to filter tasks to display all assignments on a particular day, week, or monthly view which will highlight all dates which currently have assigned tasks in a particular month. When a user selects to create a task, a new window will open displaying the relevant fields, such as Title, Due Date, Description, Add a task, Add user (If a group project). There will also be a login window and signup window for new and returning users respectively. A progress bar will also be displayed for each task, which will be filled as a user completes subtasks, or by personal selection.
</p>
<h2>Back-end Implementation</h2>
<p>The database will be utilizing MS Azure and contain information of users, such as name, username, email, password, and tasks. Email will be used as the primary key for retrieving a user’s individual information. Email will be obfuscated when viewing another user in a group setting, displaying an individual's username instead. Updates to the database will occur on task creation or completion, with some small validation to confirm a task is completed. The database will be used to integrate basic user authentication and to securely store and retrieve each user’s tasks, individual task information and settings. 
</p>
