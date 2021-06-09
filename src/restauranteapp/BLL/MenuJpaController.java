/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restauranteapp.BLL;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import restauranteapp.DAL.Encomenda;
import restauranteapp.DAL.Entidade;
import restauranteapp.DAL.Estado;
import restauranteapp.DAL.Mesas;
import restauranteapp.DAL.Pedido;
import restauranteapp.DAL.Produtoementa;

/**
 *
 * @author kevin
 */
public class MenuJpaController {
    
    private EntityManagerFactory em;
    private PedidoJpaController pc;
    private MesasJpaController mc;
    private ProdutoementaJpaController pec;
    private EncomendaJpaController ec;

    public MenuJpaController() {
        this.em = Persistence.createEntityManagerFactory("RestauranteAppPU");
        this.mc = new MesasJpaController(em);
        this.pc = new PedidoJpaController(em);
        this.pec = new ProdutoementaJpaController(em);
        this.ec = new EncomendaJpaController(em);
    }
    
    public Estado findEstadoId(int id){
       EntityManager em2 = em.createEntityManager();
       try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estado.class));
          Query q =   em2.createNamedQuery("Estado.findByIdEstado", Estado.class);
          q.setParameter("idEstado", id);
           return (Estado) q.getSingleResult();
         }catch(NoResultException e){
            return null;
        } finally {
            em2.close();
        }
    }   
    
    public Pedido findPedidoId(int id){
       EntityManager em2 = em.createEntityManager();
       try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pedido.class));
          Query q =   em2.createNamedQuery("Pedido.findByCodpedido", Pedido.class);
          q.setParameter("codpedido", id);
           return (Pedido) q.getSingleResult();
         }catch(NoResultException e){
            return null;
        } finally {
            em2.close();
        }
    }
    
    public Mesas findMesaId(int id){
       EntityManager em2 = em.createEntityManager();
       try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mesas.class));
          Query q =   em2.createNamedQuery("Mesas.findByIdMesa", Mesas.class);
          q.setParameter("idMesa", id);
           return (Mesas) q.getSingleResult();
         }catch(NoResultException e){
            return null;
        } finally {
            em2.close();
        }
    }
    
    public List<Mesas> getMesas(){   
        List<Mesas> mesas = mc.findMesasEntities();
        
        return mesas;
    }
    
    public List<Pedido> getPedidos(){
        List<Pedido> pedidos = pc.findPedidoEntities();
        return pedidos;
    }
    
    public List<Produtoementa> getProdutosEmenta(){   
        List<Produtoementa> produtos = pec.findProdutoementaEntities();
        return produtos;
    }
    
    public List<Encomenda> getEncomendas(){      
        List<Encomenda> encomendas = ec.findEncomendaEntities();
        return encomendas;
    }
    
    public void createPedido(Pedido pedido){
        pc.create(pedido);
    }
    
    public void createProdutoEmenta(Produtoementa produto){
        pec.create(produto);
    }
    
    
}
