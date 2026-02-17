import { useState, useEffect } from 'react';

export default function QuestForm() {
    const [monsters, setMonsters] = useState([]);
    const [formData, setFormData] = useState({
        name: '', difficulty: 1, reward: 1000, targetMonsterId: ''
    });
    const [status, setStatus] = useState(null);
    const [message, setMessage] = useState('');

    useEffect(() => {
        fetch('http://localhost:8080/monsters')
            .then(r => r.json())
            .then(setMonsters)
            .catch(console.error);
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setStatus(null);

        const token = localStorage.getItem('jwt_token');
        if (!token) {
            setStatus('error');
            setMessage('⚠️ Debes ser ADMIN para publicar misiones.');
            return;
        }

        // ✅ FIX: Validamos que se haya seleccionado un monstruo
        if (!formData.targetMonsterId) {
            setStatus('error');
            setMessage('⚠️ Debes seleccionar un monstruo objetivo.');
            return;
        }

        try {
            const res = await fetch('http://localhost:8080/quests', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify({
                    name: formData.name,
                    // ✅ Garantizamos tipos correctos: Number, no String
                    difficulty: parseInt(formData.difficulty),
                    reward: parseFloat(formData.reward),
                    targetMonsterId: parseInt(formData.targetMonsterId)
                })
            });

            if (res.ok) {
                setStatus('ok');
                setMessage('✅ ¡Misión publicada con éxito!');
                setFormData({ name: '', difficulty: 1, reward: 1000, targetMonsterId: '' });
            } else {
                const err = await res.json().catch(() => ({ message: `Error ${res.status}` }));
                setStatus('error');
                setMessage(`❌ ${err.message || `Error ${res.status} (¿No eres ADMIN?)`}`);
            }
        } catch (error) {
            setStatus('error');
            setMessage('Error de conexión con el servidor.');
        }
    };

    return (
        <article style={{
            backgroundColor: '#2d3436',
            border: '2px solid #5d4037',
            borderRadius: '10px',
            padding: '20px',
            color: 'white'
        }}>
            <h3 style={{ color: '#e67e22', borderBottom: '1px solid #636e72', paddingBottom: '10px' }}>
                📜 Nueva Misión
            </h3>

            {status && (
                <p style={{
                    padding: '8px 12px',
                    borderRadius: '4px',
                    backgroundColor: status === 'ok' ? '#00b894' : '#d63031',
                    color: 'white',
                    marginBottom: '12px'
                }}>
                    {message}
                </p>
            )}

            <form onSubmit={handleSubmit}>
                <div style={{ marginBottom: '10px' }}>
                    <label style={{ display: 'block', marginBottom: '4px', color: '#dfe6e9' }}>Título *</label>
                    <input
                        name="name"
                        value={formData.name}
                        onChange={e => setFormData({ ...formData, name: e.target.value })}
                        required
                        style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #636e72', backgroundColor: '#1e272e', color: 'white', boxSizing: 'border-box' }}
                    />
                </div>

                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '10px', marginBottom: '10px' }}>
                    <div>
                        <label style={{ display: 'block', marginBottom: '4px', color: '#dfe6e9' }}>Recompensa (z)</label>
                        <input
                            type="number"
                            min="0"
                            value={formData.reward}
                            onChange={e => setFormData({ ...formData, reward: e.target.value })}
                            style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #636e72', backgroundColor: '#1e272e', color: 'white', boxSizing: 'border-box' }}
                        />
                    </div>
                    <div>
                        <label style={{ display: 'block', marginBottom: '4px', color: '#dfe6e9' }}>
                            Dificultad: <strong style={{ color: '#e67e22' }}>{formData.difficulty}</strong>
                        </label>
                        <input
                            type="range"
                            min="1"
                            max="10"
                            value={formData.difficulty}
                            onChange={e => setFormData({ ...formData, difficulty: parseInt(e.target.value) })}
                            style={{ width: '100%', marginTop: '4px' }}
                        />
                    </div>
                </div>

                <div style={{ marginBottom: '15px' }}>
                    <label style={{ display: 'block', marginBottom: '4px', color: '#dfe6e9' }}>Monstruo Objetivo *</label>
                    <select
                        value={formData.targetMonsterId}
                        onChange={e => setFormData({ ...formData, targetMonsterId: e.target.value })}
                        required
                        style={{
                            width: '100%',
                            padding: '8px',
                            borderRadius: '4px',
                            border: '1px solid #636e72',
                            backgroundColor: '#1e272e',
                            color: formData.targetMonsterId ? 'white' : '#888',
                            cursor: 'pointer',
                            boxSizing: 'border-box'
                        }}
                    >
                        <option value="" disabled>Selecciona monstruo...</option>
                        {monsters.map(m => (
                            <option key={m.id} value={m.id} style={{ color: 'white', backgroundColor: '#2d3436' }}>
                                {m.name}
                            </option>
                        ))}
                    </select>
                </div>

                <button
                    type="submit"
                    style={{
                        backgroundColor: '#e67e22',
                        border: 'none',
                        width: '100%',
                        padding: '10px',
                        color: 'white',
                        fontWeight: 'bold',
                        cursor: 'pointer',
                        borderRadius: '4px',
                        fontSize: '1rem'
                    }}
                >
                    Publicar Misión
                </button>
            </form>
        </article>
    );
}