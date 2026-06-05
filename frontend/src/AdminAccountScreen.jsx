import { useEffect, useState } from 'react';

export default function AdminAccountScreen() {
  const [accounts, setAccounts] = useState([]);
  const [selectedAccount, setSelectedAccount] = useState(null);

  // Post an account
  const addAccountToCustomer = (customerId, accountData) => {
    fetch(`https://itransaction.onrender.com/api/customers/id/${customerId}/accounts`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(accountData)
    })
    .then(() => alert("Account assigned successfully!"))
    .catch(err => console.error(err));
  };

    // DELETE an account
    const removeAccount = (accountId) => {
        if (window.confirm(`Are you sure you want to delete account ${accountId}?`)) {
        fetch(`https://itransaction.onrender.com/api/customers/accounts/${accountId}`, {
          method: 'DELETE'
        })
        .then(() => {
          alert("Account deleted successfully.");
          // 🟢 Instantly wipe it from the UI list:
          setAccounts(accounts.filter(acc => acc.id !== accountId));
          // Deselect if it was the one open in the inspector
          if (selectedAccount?.id === accountId) setSelectedAccount(null);
        })
        .catch(err => console.error(err));
        }
    };

  useEffect(() => {
    // Queries your global accounts routing endpoint
//     fetch('http://localhost:8080/api/customers/accounts')
// fetch('https://itransaction-backend.onrender.com/api/customers/accounts')
 fetch(`https://itransaction.onrender.com/api/customers/accounts`)
      .then(res => res.json())
      .then(data => setAccounts(data))
      .catch(err => console.error("Error fetching global accounts ledger:", err));
  }, []);

  return (
    <div style={{ display: 'flex', gap: '40px', marginTop: '20px' }}>

      {/* LEFT SIDE: Global Vault Directory */}
      <div style={{ flex: 1 }}>
        <h3>Global Accounts Ledger (Admin View)</h3>
        <div style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
          {accounts.map(account => (
            <button
              key={account.id}
              onClick={() => setSelectedAccount(account)}
              style={{
                padding: '12px',
                textAlign: 'left',
                backgroundColor: selectedAccount?.id === account.id ? '#e2e3e5' : '#f8f9fa', // Metallic grey tint for financial oversight
                border: '1px solid #6c757d',
                borderRadius: '6px',
                cursor: 'pointer',
                fontWeight: 'bold',
                fontSize: '16px'
              }}
            >
              💳 Ledger Asset Ref: {account.id}
            </button>
          ))}
        </div>
      </div>

      {/* RIGHT SIDE: Financial Audit Inspector */}
      <div style={{ flex: 1, padding: '20px', border: '2px dashed #6c757d', borderRadius: '8px', minHeight: '200px', backgroundColor: '#fdfdfd' }}>
        <h3>Asset Vault Properties</h3>

        {selectedAccount ? (
          <div>
            <div style={{ fontSize: '36px', marginBottom: '10px' }}>
              {selectedAccount.accountType === 'SavingsAccount' ? '🐷' : selectedAccount.accountType === 'CheckingAccount' ? '🏦' : '💼'}
            </div>

            <p><strong>System Identification UUID:</strong> {selectedAccount.id}</p>
            <p><strong>Account Category Rule:</strong> <span style={{backgroundColor: '#e2e3e5', padding: '3px 6px', borderRadius: '4px', fontSize: '14px', fontFamily: 'monospace'}}>{selectedAccount.accountType}</span></p>

            <p style={{ fontSize: '20px', marginTop: '20px' }}>
              <strong>Audited Liquidity Balance: </strong>
              <span style={{
                color: selectedAccount.balance >= 0 ? '#155724' : '#721c24',
                fontWeight: 'bold'
              }}>
                ${selectedAccount.balance.toLocaleString(undefined, { minimumFractionDigits: 2, maximumFractionDigits: 2 })}
              </span>
            </p>

            <button
              onClick={() => setSelectedAccount(null)}
              style={{ marginTop: '25px', color: '#6c757d', cursor: 'pointer', border: '1px solid #6c757d', padding: '5px 10px', borderRadius: '4px', background: 'none' }}
            >
              Release Ledger Lock
            </button>
            <button
              onClick={() => removeAccount(selectedAccount.id)}
              style={{ marginTop: '25px', color: '#fff', backgroundColor: '#dc3545', cursor: 'pointer', border: 'none', padding: '6px 12px', borderRadius: '4px', fontWeight: 'bold' }}
            >
              🚨 Terminate Asset Account
            </button>
          </div>
        ) : (
          <p style={{ color: '#383d41', fontStyle: 'italic' }}>
            Select an ongoing liability or asset pointer from the left column to run an instant capitalization audit.
          </p>
        )}
      </div>

    </div>
  );
}