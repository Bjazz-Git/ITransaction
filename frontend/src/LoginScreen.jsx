import { useState } from 'react';

export default function LoginScreen({ onLoginSuccess }) {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    setErrorMessage('');

    if (!username || !password) {
      setErrorMessage('Please fill in all fields.');
      return;
    }

    // Hit your Spring Boot Controller to verify credentials
//     fetch(`http://localhost:8080/api/customers/name/${username}`)
//         fetch(`https://your-spring-backend.onrender.com/api/customers/name/${username}`)
       fetch(`https://itransaction.onrender.com/api/customers/name/${username}`)
      .then((res) => {
        if (!res.ok) throw new Error('User not found');
        return res.json();
      })
      .then((user) => {
        // Simple credential match check
        if (user.password === password) {
          onLoginSuccess(user); // 🔥 Passing 'user' data up to App.jsx safely
        } else {
          setErrorMessage('Invalid password. Please try again.');
        }
      })
      .catch((err) => {
        setErrorMessage('Username not found in our database.');
      });
  };

  return (
    <div style={{ maxWidth: '400px', margin: '100px auto', padding: '30px', border: '1px solid #ccc', borderRadius: '8px', textAlign: 'center', fontFamily: 'sans-serif' }}>
      <h2>Bank Portal Login</h2>
      {errorMessage && <p style={{ color: 'red', fontWeight: 'bold' }}>{errorMessage}</p>}

      <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '15px' }}>
        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          style={inputStyle}
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          style={inputStyle}
        />
        <button type="submit" style={buttonStyle}>Sign In</button>
      </form>
    </div>
  );
}

const inputStyle = { padding: '10px', fontSize: '16px', borderRadius: '4px', border: '1px solid #ccc' };
const buttonStyle = { padding: '12px', fontSize: '16px', backgroundColor: '#007bff', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer', fontWeight: 'bold' };