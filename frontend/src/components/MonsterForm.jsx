import { useState } from 'react';

export default function MonsterForm() {
    const [formData, setFormData] = useState({
        name: '', type: '', element: '', weakness: '', threatLevel: 1, imageUrl: ''
    });
    const [status, setStatus] = useState(null); // null | 'ok' | 'error'
    const [message, setMessage] = useState('');

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: name === 'threatLevel' ? parseInt(value) : value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setStatus(null);

        const token = localStorage.getItem('jwt_token');
        if (!token) {
            setStatus('error');
            setMessage('⚠️ No estás logueado. Inicia sesión.');
            return;
        }

        try {
            const res = await fetch('http://localhost:8080/monsters', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify({
                    ...formData,

                    threatLevel: Number(formData.threatLevel)
                })
            });

            if (res.ok) {
                setStatus('ok');
                setMessage('✅ ¡Monstruo registrado con éxito!');
                setFormData({ name: '', type: '', element: '', weakness: '', threatLevel: 1, imageUrl: '' });
                window.dispatchEvent(new Event('monsters-updated'));
            } else {
                const err = await res.json().catch(() => ({ message: `Error ${res.status}` }));
                setStatus('error');
                setMessage(`❌ ${err.message || `Error ${res.status}`}`);
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
            <h3 style={{ color: '#ff7675', borderBottom: '1px solid #636e72', paddingBottom: '10px' }}>
                🦖 Avistar Monstruo
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
                    <label style={{ display: 'block', marginBottom: '4px', color: '#dfe6e9' }}>Nombre *</label>
                    <input
                        name="name"
                        value={formData.name}
                        onChange={handleChange}
                        required
                        style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #636e72', backgroundColor: '#1e272e', color: 'white', boxSizing: 'border-box' }}
                    />
                </div>

                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '10px', marginBottom: '10px' }}>
                    <div>
                        <label style={{ display: 'block', marginBottom: '4px', color: '#dfe6e9' }}>Tipo *</label>
                        <input
                            name="type"
                            value={formData.type}
                            onChange={handleChange}
                            required
                            style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #636e72', backgroundColor: '#1e272e', color: 'white', boxSizing: 'border-box' }}
                        />
                    </div>
                    <div>
                        <label style={{ display: 'block', marginBottom: '4px', color: '#dfe6e9' }}>Elemento</label>
                        <input
                            name="element"
                            value={formData.element}
                            onChange={handleChange}
                            placeholder="Fire, Water, Ice..."
                            style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #636e72', backgroundColor: '#1e272e', color: 'white', boxSizing: 'border-box' }}
                        />
                    </div>
                </div>

                <div style={{ marginBottom: '10px' }}>
                    <label style={{ display: 'block', marginBottom: '4px', color: '#dfe6e9' }}>Debilidad *</label>
                    <input
                        name="weakness"
                        value={formData.weakness}
                        onChange={handleChange}
                        required
                        style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #636e72', backgroundColor: '#1e272e', color: 'white', boxSizing: 'border-box' }}
                    />
                </div>

                <div style={{ marginBottom: '15px' }}>
                    <label style={{ display: 'block', marginBottom: '4px', color: '#dfe6e9' }}>
                        Nivel de Amenaza: <strong style={{ color: '#ff7675' }}>{formData.threatLevel}</strong>
                    </label>
                    <input
                        type="range"
                        name="threatLevel"
                        min="1"
                        max="10"
                        value={formData.threatLevel}
                        onChange={handleChange}
                        style={{ width: '100%' }}
                    />
                </div>

                <div style={{ marginBottom: '15px' }}>
                    <label style={{ display: 'block', marginBottom: '4px', color: '#dfe6e9' }}>Imagen URL</label>
                    <input
                        type="url"
                        name="imageUrl"
                        value={formData.imageUrl}
                        onChange={handleChange}
                        placeholder="https://..."
                        style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #636e72', backgroundColor: '#1e272e', color: 'white', boxSizing: 'border-box' }}
                    />
                </div>

                <button
                    type="submit"
                    style={{
                        backgroundColor: '#c0392b',
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
                    Registrar Monstruo
                </button>
            </form>
        </article>
    );
}