function checkAnswer(event, correctAnswer, userId, taskId) {
    event.preventDefault(); // Prevent the form from submitting normally

    var form = event.target;
    var userAnswer = form.querySelector('.input').value.trim().toLowerCase();
    var button = form.querySelector('.button');

    if (userAnswer === correctAnswer.toLowerCase()) {
        var task = form.closest('.task');
        var icon = task.querySelector('.icon-correct-answer');
        var header = task.querySelector('.task-header-correct-answer');

        icon.style.display = 'block'; // Show the icon
        header.style.borderLeftColor = 'rgb(32, 199, 32)'; // Change the border color
        button.style.backgroundColor = 'green'; // Change button color to green
        button.value = 'Correct Answer'; // Change button text to 'Correct Answer'
        button.disabled = true; // Disable the button

        // Send AJAX request to update the database
        fetch('../../../javaScript/updateTasksCompleted.jsp', { // Adjust the number of `../` based on actual directory depth
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `userId=${encodeURIComponent(userId)}&moduleId=1&taskId=${encodeURIComponent(taskId)}`
        })
        .then(response => response.text())
        .then(data => console.log(data))
        .catch(error => console.error('Error:', error));
    } else {
        button.style.backgroundColor = 'red'; // Change button color to red
        setTimeout(function() {
            button.style.backgroundColor = ''; // Reset button color
        }, 1000); // Reset button color after 1 second
    }
}


// show and hide tasks from header
document.addEventListener('DOMContentLoaded', function() {
    var headers = document.querySelectorAll('.task-header');

    headers.forEach(function(header) {
        header.addEventListener('click', function() {
            // Find the current task's content
            var taskContent = this.nextElementSibling;

            // Hide all task contents
            document.querySelectorAll('.task-content').forEach(function(content) {
                if (content !== taskContent) {
                    content.style.display = 'none'; // Hide other task contents
                }
            });

            // Toggle the visibility of the current task's content
            taskContent.style.display = taskContent.style.display === 'none' || taskContent.style.display === '' ? 'block' : 'none';
        });
    });
});