declare global {
    interface Window {
        _env_: { [key: string]: any };
    }
}

export const API = 
    import.meta.env.VITE_API