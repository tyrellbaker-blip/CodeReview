const fs = require('fs');
const readline = require('readline');

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});

const users = {};
const posts = {};

function parseUserLine(line) {
  const [username, displayName, state, friendsList] = line.split(';');
  const friends = friendsList.slice(1, -1).split(',');
  return { username, displayName, state, friends };
}

function parsePostLine(line) {
  const [postId, userId, visibility] = line.split(';');
  return { postId, userId, visibility };
}

function loadUsers(filePath) {
  const lines = fs.readFileSync(filePath, 'utf-8').trim().split('\n');
  lines.forEach(line => {
    const user = parseUserLine(line);
    users[user.username] = user;
  });
}

function loadPosts(filePath) {
  const lines = fs.readFileSync(filePath, 'utf-8').trim().split('\n');
  lines.forEach(line => {
    const post = parsePostLine(line);
    posts[post.postId] = post;
  });
}

function checkVisibility(postId, username) {
  const post = posts[postId];
  const user = users[username];
  if (!post || !user) {
    console.log('Post or user not found');
    return;
  }
  if (post.visibility === 'public' || post.userId === username || (post.visibility === 'friend' && users[post.userId].friends.includes(username))) {
    console.log('Access Permitted');
  } else {
    console.log('Access Denied');
  }
}

function retrievePosts(username) {
  const accessiblePosts = Object.values(posts).filter(post =>
    post.userId !== username && (post.visibility === 'public' || (post.visibility === 'friend' && users[post.userId].friends.includes(username)))
  ).map(post => post.postId);

  console.log(accessiblePosts.join(', '));
}

function searchUsersByLocation(state) {
  const foundUsers = Object.values(users).filter(user => user.state === state).map(user => user.displayName);
  console.log(foundUsers.join(', '));
}

function mainMenu() {
  rl.question('\n1. Load input data\n2. Check visibility\n3. Retrieve posts\n4. Search users by location\n5. Exit\nChoose an option: ', (option) => {
    switch (option) {
      case '1':
        rl.question('Enter user info file path: ', (userInfoPath) => {
          rl.question('Enter post info file path: ', (postInfoPath) => {
            loadUsers(userInfoPath);
            loadPosts(postInfoPath);
            console.log('Data loaded successfully');
            mainMenu();
          });
        });
        break;
      case '2':
        rl.question('Enter post ID: ', (postId) => {
          rl.question('Enter username: ', (username) => {
            checkVisibility(postId, username);
            mainMenu();
          });
        });
        break;
      case '3':
        rl.question('Enter username: ', (username) => {
          retrievePosts(username);
          mainMenu();
        });
        break;
      case '4':
        rl.question('Enter state: ', (state) => {
          searchUsersByLocation(state);
          mainMenu();
        });
        break;
      case '5':
        rl.close();
        break;
      default:
        console.log('Invalid option. Please try again.');
        mainMenu();
    }
  });
}

rl.on('close', () => {
  console.log('Exiting program.');
});

mainMenu();

